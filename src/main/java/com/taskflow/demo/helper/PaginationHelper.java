package com.taskflow.demo.helper;

import com.taskflow.demo.config.PaginationConfig;
import com.taskflow.demo.exception.InvalidPageSizeException;
import com.taskflow.demo.exception.InvalidSortParameterException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PaginationHelper {

    private final PaginationConfig paginationConfig;

    public PaginationHelper(PaginationConfig paginationConfig) {
        this.paginationConfig = paginationConfig;
        }

    public Pageable buildPageable(Integer page, Integer size, String sortBy, String sortDir){
        //Step 1 — Apply default if missing
        if(size == null){
            size = paginationConfig.getDefaultPageSize();
        }
        if(sortBy == null){
            sortBy= paginationConfig.getSortBy();
        }
        //Step 2 — Validate max size
        if(size > paginationConfig.getMaxPageSize()){
            throw new InvalidPageSizeException("Page size is too big");
        }
        if(!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")){
            throw new InvalidSortParameterException("Invalid sortBy Direc");
        }

        // Allowed sort fields
        Set<String> allowedSortFields = Set.of("id", "name", "email");

        if(!allowedSortFields.contains(sortBy)){
            throw new InvalidSortParameterException("Invalid sortBy paramter");
        }

        // Convert "asc"/"desc" string into Spring Direction enum
        // Convert string → enum
        Sort.Direction direction = Sort.Direction.fromString(sortDir);

        // Create Sort object
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }


}
