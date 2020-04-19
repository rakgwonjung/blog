package com.rock.boottutorial.repository;

import com.rock.boottutorial.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

// 메시지 저장을 담당하는 저장소
// @Component 은 제네릭 스테레오 타입이다 클래스에 이 어노테이션이 있으면 해당 클래스를 인스턴스화 한다
@Component
public class MessageRepository {

    private static final Logger logger = LoggerFactory.getLogger(MessageRepository.class);

//    private DataSource dataSource;

    // jdbcTemplate
//    private NamedParameterJdbcTemplate jdbcTemplate;

    // ***
    // ***** 하이버네이트 사용
    // ***** AppConfig 에 Bean 주입 LocalSessionFactoryBean
    // ***** SessionFactory 의 인스턴스를 주입
    // ***
    private SessionFactory sessionFactory;

    public MessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Message saveMessage(Message message) {
        // Session 인스턴스를 획득
//        Session session = sessionFactory.openSession();

        // 트랜잭션 어드바이저와 공유하는 현재의 하이버네이트 컨텍스트에 있는 현재 세션을 획득한다.
        // 이러한 방식으로 트랜잭션 어드바이저는 에러가 발생했을 때 저장된 메시지를 롤백할 수 있다.
        Session session = sessionFactory.getCurrentSession();
        // message 객체를 저장하는 session 객체의 save 메서드 사용
        // 하이버네이트를 사용하면 message 객체의 생성된 id를 얻는 것에 대해 걱정할 필요 없다
        // 스프링 JDBC 만을 사용할 때 처럼 GeneratedKeyHolder holder = new GeneratedKeyHolder(); XXXX
        // 지금 해야 할 일은 단순히 message 객체를 저장한 후에 반환하는 것이다.
        // 불필요한 코드를 절약하고 비지니스 로직에만 집중할 수 있다.
        session.save(message);
        return message;
    }

    public List<Message> getMessages() {
        // 세션을 얻는다
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Message";
        // HQL (Hibernate Query Language) 생성
        Query<Message> query = session.createQuery(hql, Message.class);
        return query.list();
    }

    // ***
    // ***** 스프링 JDBC를 사용
    // ***

    // NamedParameterJdbcTemplate 인스터스를 인스턴스화할 수 있도록 스프링에 DataSource의 주입을 요청하는 세터 메서드
//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//    }

//    public Message saveMessage(Message message) {
//        // 새 메시지의 생성된 id를 보관할 GeneratedKeyHolder를 선언
//        GeneratedKeyHolder holder = new GeneratedKeyHolder();
//        // 매개변수 준비
//        // 플레이스 홀더의 이름은 매개변수에 사용된 이름과 일치해야한다 text, createdDate
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("text", message.getText());
//        params.addValue("createdDate", message.getCreatedDate());
//        // 이름을 지정한 매개변수 text와 createdDate를 플레이스홀더로 사용
//        String insertSQL = "INSERT INTO messages (`id`, `text`, `created_date`) VALUE (null, :text, :createdDate)";
//
//        // 쿼리를 실행하기 위해 jdbcTemplate에 요청
//        try {
//            this.jdbcTemplate.update(insertSQL, params, holder);
//        } catch (DataAccessException e) {
//            logger.error("Failed to save message", e);
//            return null;
//        }
//        // 새롭게 저장된 메시지를 반환
//        return new Message(holder.getKey().intValue(), message.getText(), message.getCreatedDate());
//    }

    // ***
    // ***** JDBC API를 직접 사용
    // ***

    // DataSource에서 데이터베이스 연결을 얻을 수 있도록 DataSource 인스턴스의 주입
//    public MessageRepository(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    public Message saveMessage(Message message) {
//        // 스프링의 헬퍼(helper) 클래스인 DataSourceUtils
//        Connection c = DataSourceUtils.getConnection(dataSource);
//        try {
//            String insertSql = "INSERT INTO messages ('id', 'text', 'created_date') VALUE (null, ?, ?)";
//            // 쿼리를 날리고 생성된 메시지의 id를 반환 받기 위해 Statement.RETURN_GENERATED_KEYS 추가
//            PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
//            // Prepare the parameters for the SQL
//            ps.setString(1, message.getText());
//            ps.setTimestamp(2, new Timestamp(message.getCreatedDate().getTime()));
//            int rowsAffected = ps.executeUpdate();
//            if (rowsAffected > 0) {
//                // Getting the newly saved message id
//                ResultSet result = ps.getGeneratedKeys();
//                if (result.next()) {
//                    int id = result.getInt(1);
//                    return new Message(id, message.getText(), message.getCreatedDate());
//                } else {
//                    logger.error("Failed to retrieve id. No row in result set");
//                    return null;
//                }
//            } else {
//                // Insert did not succeed
//                return null;
//            }
//        } catch (SQLException ex) {
//            logger.error("Failed to save message", ex);
//            try {
//                c.close();
//            } catch (SQLException e) {
//                logger.error("Failed to close connection", e);
//            }
//        } finally {
//            DataSourceUtils.releaseConnection(c, dataSource);
//        }
//        return null;
//    }
}
