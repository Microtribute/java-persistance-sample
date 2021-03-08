package com.organization.web.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto implements Serializable {

    Long id;

    String referenceId;

    String title;

    String shortDescription;

    String description;

    CategoryDto parent;

    CategoryPageDto categoryPage;

    List<CategoryDto> children;
}

