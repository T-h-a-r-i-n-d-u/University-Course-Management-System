package com.ucms.ucmsapi.course.dto;

public record CourseUpsertRequest(
        String code,
        String title,
        String description,
        Integer credits,
        String semester
) {}
