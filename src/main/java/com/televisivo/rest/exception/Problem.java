package com.televisivo.rest.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Problem {

    private Integer status;
	private LocalDateTime timeStamp;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
    private List<Fields> fields;
    
    public static Builder builder() {
		return new Builder();
    }
    
    public static class Builder {
    
        private Problem problem;

        public Builder() {
			this.problem = new Problem();
        }
        
        public Builder addStatus(Integer status) {
            this.problem.status = status;
            return this;
        }
        
        public Builder addTimestamp(LocalDateTime timestamp) {
            this.problem.timeStamp = timestamp;
            return this;
        }
        
        public Builder addType(String type) {
            this.problem.type = type;
            return this;
        }
        
        public Builder addTitle(String title) {
            this.problem.title = title;
            return this;
        }
        
        public Builder addDetail(String detail) {
            this.problem.detail = detail;
            return this;
        }
        
        public Builder addUserMessage(String userMessage) {
            this.problem.userMessage = userMessage;
            return this;
        }
        
        public Builder addListFields(List<Fields> fields) {
            this.problem.fields = fields;
            return this;
        }
        
        public Problem build() {
            return this.problem;
        }
    }
}