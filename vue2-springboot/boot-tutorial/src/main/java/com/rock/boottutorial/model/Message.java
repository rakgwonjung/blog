package com.rock.boottutorial.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

//@SpringBootApplication
// Message 모델을 정의
// @Entity - Message 클래스를 Entity 클래스로 표시
@Entity
// @Table - Message 클래스와 매핑되는 messages xㅔ이블을 지정하기 위함
@Table(name = "messages")
public class Message {

	// @Id - 기본키
	@Id
	// @GeneratedValue - id 값이 생성되는 방법을 지정, 아래의 방법은 auto_increment
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 컬럼 매핑, null 허용하지 않음
	@Column(name = "id", nullable = false)
	private Integer id;

	// 컬럼 매핑, 널 허용하지 않음, 길이 128
	@Column(name = "text", nullable = false, length = 128)
	private String text;

	// 컬럼 매핑, 널 허용하지 않음
	@Column(name = "created_date", nullable = false)
	// java.util.Date, java.util.Calendar 타입의 필드에 필수적으로 추가 해야 한다.
	// @Temporal(TemporalType.TIMESTAMP) 값은 createdDate필드를 JDBC가 이해할 수 있는 java.sql.Timestamp 타입의 값과 매핑
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public Message(String text) {
		this.text = text;
		this.createdDate = new Date();
	}

	public Message(int id, String text, Date createdDate) {
		this.id = id;
		this.text = text;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return Objects.equals(id, message.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
