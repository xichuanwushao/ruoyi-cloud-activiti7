package com.ruoyi.danny.domain.dto;

/**
 */
public class HistoryFormDataDTO {
    private String title;
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HistoryFormDataDTO() {
    }

    public HistoryFormDataDTO(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
