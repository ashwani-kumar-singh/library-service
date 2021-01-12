package com.jpop.libraryservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(doNotUseGetters = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookIssueResponse {

    private  Integer id;

    @JsonProperty(value = "book_id")
    private Integer bookId;

    @JsonProperty(value = "user_id")
    private Integer userId;

    @JsonProperty(value = "created_at")
    private Date createdAt;
}
