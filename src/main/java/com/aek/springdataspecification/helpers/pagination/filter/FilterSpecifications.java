/*
 * Copyright (c) 2019. @boottech - (boottechnologies@hotmail.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.aek.springdataspecification.helpers.pagination.filter;

import com.aek.springdataspecification.helpers.pagination.SearchFilters;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>FilterSpecifications</h2>
 *
 * @author boottech
 * <p>
 * Description:
 */
public class FilterSpecifications<T> implements Specification<T> {


    private final List<FilterCondition> filterAndConditions;
    private final List<FilterCondition> filterOrConditions;

    public FilterSpecifications(SearchFilters searchFilters) {
        this.filterAndConditions = searchFilters.filterAndConditions();
        this.filterOrConditions = searchFilters.filterOrConditions();
    }

    @Override
    public Predicate toPredicate(Root root, @Nullable CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Assert.notNull(criteriaQuery, "criteriaQuery must not be null");

        List<Predicate> predicatesAndClause = new ArrayList<>();
        List<Predicate> predicatesOrClause = new ArrayList<>();

        filterAndConditions.forEach(condition -> predicatesAndClause.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        filterOrConditions.forEach(condition -> predicatesOrClause.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));

        if (!CollectionUtils.isEmpty(predicatesAndClause) && !CollectionUtils.isEmpty(predicatesOrClause)) {
            return criteriaBuilder.and(criteriaBuilder.and(predicatesAndClause.toArray(new Predicate[0])),
                    criteriaBuilder.or(criteriaBuilder.or(predicatesOrClause.toArray(new Predicate[0]))));
        }

        if (!CollectionUtils.isEmpty(predicatesAndClause)) {
            return criteriaBuilder.and(predicatesAndClause.toArray(new Predicate[0]));
        }

        if (!CollectionUtils.isEmpty(predicatesOrClause)) {
            return criteriaBuilder.or(predicatesOrClause.toArray(new Predicate[0]));
        }

        return null;
    }


    private Predicate buildPredicate(FilterCondition condition, Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        return switch (condition.operator()) {
            case EQUAL -> criteriaBuilder.equal(root.get(condition.field()), condition.value());
            case NOT_EQUAL -> criteriaBuilder.notEqual(root.get(condition.field()), condition.value());
             /*
                Operator for any type
             */
            case IS_NULL -> criteriaBuilder.isNull(root.get(condition.field()));
            case IS_NOT_NULL -> criteriaBuilder.isNotNull(root.get(condition.field()));
            /*
                Operator for String type
             */
            case IS_EMPTY -> criteriaBuilder.equal(root.get(condition.field()), "");
            case IS_NOT_EMPTY -> criteriaBuilder.notEqual(root.get(condition.field()), "");
            case CONTAINS ->
                    criteriaBuilder.like(root.get(condition.field()).as(String.class), "%" + condition.value() + "%");
            case NOT_CONTAINS ->
                    criteriaBuilder.notLike(root.get(condition.field()).as(String.class), "%" + condition.value() + "%");
            case START_WITH ->
                    criteriaBuilder.like(root.get(condition.field()).as(String.class), condition.value() + "%");
            case END_WITH ->
                    criteriaBuilder.like(root.get(condition.field()).as(String.class), "%" + condition.value());
            case GREATER_THAN ->
                    criteriaBuilder.greaterThan(root.get(condition.field()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.value()));
            case GREATER_THAN_OR_EQUAL_TO ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(condition.field()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.value()));
            case LESS_THAN ->
                    criteriaBuilder.lessThan(root.get(condition.field()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.value()));
            case LESS_THAN_OR_EQUAL_TO ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(condition.field()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.value()));
            case JOIN -> criteriaBuilder.equal(root.join(condition.field()).get("id"), condition.value());

            default -> throw new IllegalArgumentException("Bad argument for operator param");
        };
    }


    private LocalDateTime stringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
    }
}
