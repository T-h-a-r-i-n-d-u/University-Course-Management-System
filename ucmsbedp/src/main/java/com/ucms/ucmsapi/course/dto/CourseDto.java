package com.ucms.ucmsapi.course.dto;

public record CourseDto(
        Long id,
        String code,
        String title,
        String description,
        Integer credits,
        String semester
) {}
