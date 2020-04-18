package com.rock.boottutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

// 메시지 저장을 담당하는 저장소
// @Component 은 제네릭 스테레오 타입이다 클래스에 이 어노테이션이 있으면 해당 클래스를 인스턴스화 한다
@Component
public class MessageRepository {

    private static final Log logger = LogFactory.getLog(MessageRepository.class);

    private DataSource dataSource;

    // DataSource에서 데이터베이스 연결을 얻을 수 있도록 DataSource 인스턴스의 주입
    public MessageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Message saveMessage(Message message) {
        // 스프링의 헬퍼(helper) 클래스인 DataSourceUtils
        Connection c = DataSourceUtils.getConnection(dataSource);
        try {
            String insertSql = "INSERT INTO messages ('id', 'text', 'created_date') VALUE (null, ?, ?)";
            // 쿼리를 날리고 생성된 메시지의 id를 반환 받기 위해 Statement.RETURN_GENERATED_KEYS 추가
            PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            // Prepare the parameters for the SQL
            ps.setString(1, message.getText());
            ps.setTimestamp(2, new Timestamp(message.getCreatedDate().getTime()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Getting the newly saved message id
                ResultSet result = ps.getGeneratedKeys();
                if (result.next()) {
                    int id = result.getInt(1);
                    return new Message(id, message.getText(), message.getCreatedDate());
                } else {
                    logger.error("Failed to retrieve id. No row in result set");
                    return null;
                }
            } else {
                // Insert did not succeed
                return null;
            }
        } catch (SQLException ex) {
            logger.error("Failed to save message", ex);
            try {
                c.close();
            } catch (SQLException e) {
                logger.error("Failed to close connection", e);
            }
        } finally {
            DataSourceUtils.releaseConnection(c, dataSource);
        }
        return null;
    }
}
