package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dto.SearchResult;
import com.lzj.fruit.dto.UserCriteria;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.util.DBUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
public class UserDaoImpl implements UserDao {

    public static final String UPDATE_USER_SQL = "UPDATE user SET %s WHERE id=?";
    public static final String GET_USERS_SQL = "SELECT id, username, email, phone, birth_date FROM user WHERE 1=1";
    public static final String GET_USERS_COUNT_SQL = "SELECT COUNT(*) FROM user WHERE 1=1";
    public static final String GET_USER_BY_USERNAME_OR_EMAIL_SQL = "SELECT id, username, email, phone, birth_date, pwdHash FROM user where username=? OR email=?";
    public static final String DELETE_USER_BY_ID_SQL = "DELETE FROM user WHERE id=?";
    public static final String ADD_USER_SQL = "INSERT INTO user(username, pwdHash, email, phone, birth_date) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_USER_BY_ID_SQL = "SELECT id, username, pwdHash, email, phone, birth_date FROM user where id=?";

    @Override
    public User create(User user) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPwdHash());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setDate(5, new Date(user.getBirthDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys(); // 获取生成的主键
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1)); // 只能用columnIndex参数
            }
            return getById(user.getId()).get();
//            Date now = new Date();
//            java.sql.Date date = new java.sql.Date(now.getTime());
//            stmt.executeUpdate(String.format("INSERT INTO user(username, pwdHash, email,  phone) VALUES ('%s', '%s', '%s', '%s')",
//                    user.getUsername(), user.getPwdHash(), user.getEmail(), user.getPhone()));
        }
    }

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_USERNAME_OR_EMAIL_SQL)
        ) {
            // SELECT id, username, email, phone FROM user where username=lzj
//            ResultSet rs = stmt.executeQuery(String.format("SELECT id, username, email, phone FROM user where username='%s'", username));

            pstmt.setString(1, usernameOrEmail);
            pstmt.setString(2, usernameOrEmail);
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) { // 将指针移动到第一条记录（如果有记录）
                user = getUser(rs);
            }
            return user;
        }
    }

    @Override
    public SearchResult<User> searchUsers(UserCriteria criteria) throws Exception {
        // 初始化查询sql，使用1=1来构建条件子句
        StringBuilder sql = new StringBuilder(GET_USERS_SQL);
        StringBuilder countSql = new StringBuilder(GET_USERS_COUNT_SQL);
        List<Object> params = new ArrayList<>();

        // 根据criteria不同的属性，构建条件子句，添加参数
        if (notNullOrEmpty(criteria.getUsername())) {
            sql.append(" AND username LIKE ?");
            countSql.append(" AND username LIKE ?");
            params.add(criteria.getUsername());
        }
        if (notNullOrEmpty(criteria.getEmail())) {
            sql.append(" AND email=?");
            countSql.append(" AND email=?");
            params.add(criteria.getEmail());
        }
        if (Objects.nonNull(criteria.getBirthday())) {
            sql.append(" AND birth_date=?");
            countSql.append(" AND birth_date=?");
            params.add(criteria.getBirthday());
        }

        if (criteria.getPageSize() <= 0) {
            criteria.setPageSize(UserCriteria.DEFAULT_PAGE_SIZE);
        }
        if (criteria.getPageNumber() < 0) {
            criteria.setPageNumber(0);
        }

        // 排序
        if (notNullOrEmpty(criteria.getSortBy())) {
            sql.append(String.format(" ORDER BY %s %s", criteria.getSortBy(), criteria.getSortDirection()));
        }

        // offset = pageNumber * pageSize
        sql.append(String.format(" LIMIT %d, %d",
                criteria.getPageNumber() * criteria.getPageSize(), criteria.getPageSize()));

        SearchResult result = new SearchResult();
        result.setPageSize(criteria.getPageSize());
        result.setPageNumber(criteria.getPageNumber());

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql.toString());
             PreparedStatement countPstmt = connection.prepareStatement(countSql.toString());
        ) {
            if (notNullOrEmpty(criteria.getUsername())) {
                int idx = params.indexOf(criteria.getUsername());
                pstmt.setString(idx + 1, "%" + criteria.getUsername() + "%");
                countPstmt.setString(idx + 1, "%" + criteria.getUsername() + "%");
            }
            if (notNullOrEmpty(criteria.getEmail())) {
                int idx = params.indexOf(criteria.getEmail());
                pstmt.setString(idx + 1, criteria.getEmail());
                countPstmt.setString(idx + 1, criteria.getEmail());
            }
            if (Objects.nonNull(criteria.getBirthday())) {
                int idx = params.indexOf(criteria.getBirthday());
                pstmt.setDate(idx + 1, new Date(criteria.getBirthday().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                countPstmt.setDate(idx + 1, new Date(criteria.getBirthday().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            }
            ResultSet rs = pstmt.executeQuery();
            ResultSet countRs = countPstmt.executeQuery(); // 获取数量总记录数量
            List<User> users = new ArrayList<>();
            while (rs.next()) { // 将指针移动到下一条记录（如果有记录）
                User user = getUser(rs);

                users.add(user);
            }

            countRs.next();
            long total = countRs.getLong(1);
            result.setTotal(total);

            long totalPages = total / criteria.getPageSize();
            totalPages = totalPages * criteria.getPageSize() < total ? totalPages + 1 : totalPages;
            result.setTotalPages(totalPages);

            result.setData(users);
            return result;
        }
    }

    private static User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPwdHash(rs.getString("pwdHash"));
        Date birthDate = rs.getDate("birth_date");
        if (Objects.nonNull(birthDate)) {
            user.setBirthDate(birthDate.toLocalDate());
        }
        return user;
    }

    @Override
    public User update(User user) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库
        Optional<User> dbUser = getById(user.getId());
        dbUser.orElseThrow(() -> new NotFoundException(String.format("没有找到用户，id: %d", user.getId())));

        List<String> segments = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        if (notNullOrEmpty(user.getPhone())) {
            segments.add("phone=?");
            fields.add("phone");
        }
        if (notNullOrEmpty(user.getEmail())) {
            segments.add("email=?");
            fields.add("email");
        }
        if (notNullOrEmpty(user.getPwdHash())) {
            segments.add("pwdHash=?");
            fields.add("pwdHash");
        }
        String updateSegment = String.join(",", segments);
        String sql = String.format(UPDATE_USER_SQL, updateSegment);

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            if (notNullOrEmpty(user.getPhone())) {
                int idx = fields.indexOf("phone");
                pstmt.setString(idx + 1, user.getPhone());
            }
            if (notNullOrEmpty(user.getEmail())) {
                int idx = fields.indexOf("email");
                pstmt.setString(idx + 1, user.getEmail());
            }
            if (notNullOrEmpty(user.getPwdHash())) {
                int idx = fields.indexOf("pwdHash");
                pstmt.setString(idx + 1, user.getPwdHash());
            }
            pstmt.setLong(fields.size() + 1, user.getId());

            pstmt.executeUpdate();
        }
        return user;
    }

    @Override
    public void delete(long id) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库
        Optional<User> user = getById(id);
        user.orElseThrow(() -> new NotFoundException(String.format("没有找到用户，id: %d", id)));
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_USER_BY_ID_SQL)
        ) {
//            stmt.executeUpdate(String.format("DELETE FROM user WHERE id=%d", id));
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }


    private Optional<User> getById(long id) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_ID_SQL)
        ) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) { // 将指针移动到第一条记录（如果有记录）
                user = getUser(rs);
            }
            return Optional.ofNullable(user); // 返回一个Optional对象，其中的值可以为null
        }
    }

    private static boolean notNullOrEmpty(String str) {
        return Objects.nonNull(str) &&
                !str.trim().isEmpty();
    }
}
