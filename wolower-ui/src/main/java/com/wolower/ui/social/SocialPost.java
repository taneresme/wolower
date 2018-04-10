package com.wolower.ui.social;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class SocialPost {
	@Getter
	@Setter
	private String socialMedia;
	@Getter
	@Setter
	private Long socialUserId;
	@Getter
	@Setter
	private String socialUserName;
	@Getter
	@Setter
	private Long amount;
	@Getter
	@Setter
	private String currency;
	@Getter
	@Setter
	private LocalDateTime postDate;
	@Getter
	@Setter
	private Long postId;
	@Getter
	@Setter
	private String postText;
	@Getter
	private Boolean isReply;
	@Getter
	private Long repliedPostId;

	public void setRepliedPostId(Long repliedPostId) {
		this.repliedPostId = repliedPostId;
		this.isReply = !(repliedPostId == null);
	}

}
