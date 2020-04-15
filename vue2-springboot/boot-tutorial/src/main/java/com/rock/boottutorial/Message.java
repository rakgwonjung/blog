package com.rock.boottutorial;

//@SpringBootApplication
// Message 모델을 정의
public class Message {

	private String text;

	public Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
