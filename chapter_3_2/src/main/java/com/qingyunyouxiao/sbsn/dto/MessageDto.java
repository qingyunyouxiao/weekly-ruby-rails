package com.qingyunyouxiao.sbsn.dto;

public class MessageDto {
    
    private Long id;
    private String content;

    public MessageDto() {
        super();
    }

    public MessageDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }   
}
