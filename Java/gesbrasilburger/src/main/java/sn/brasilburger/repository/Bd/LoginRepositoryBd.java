package sn.brasilburger.repository.Bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import sn.brasilburger.config.database.Database;
import sn.brasilburger.entity.RoleUser;
import sn.brasilburger.entity.User;
import sn.brasilburger.repository.LoginRepository;

public class LoginRepositoryBd implements LoginRepository{
    private Database database;
    private static LoginRepositoryBd instance = null;
    public LoginRepositoryBd(Database database) {
        this.database = database;
    }
    public static Object getInstance(Database database){
        if (instance == null) {
            instance = new LoginRepositoryBd(database);
        }
        return instance;
    }
    @Override
    public Optional<User> getUserByEmail(String email) {
            Connection conn = database.getConnection();
            PreparedStatement ps;
        try {
            ps = conn.prepareStatement("SELECT * FROM \"user\" WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(toEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty(); 
    }
    @Override
    public boolean addUser(User user) {
        String sql = """
            INSERT INTO "user" (nom_complet, telephone, email, password, role)
            VALUES (?, ?, ?, ?, ?::role_user)
        """;
        Connection conn = database.getConnection();
        PreparedStatement ps;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getNomComplet());
            ps.setString(2, user.getTelephone());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setObject(5, user.getRole().name(), java.sql.Types.OTHER);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private User toEntity(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setNomComplet(rs.getString("nom_complet"));
        u.setTelephone(rs.getString("telephone"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(RoleUser.valueOf(rs.getString("role")));
        return u;
    }
}
