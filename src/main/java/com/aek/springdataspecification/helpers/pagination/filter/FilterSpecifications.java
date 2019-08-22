/*
 * Copyright (c) 2019. @aek - (anicetkeric@gmail.com)
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

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h2>FilterSpecifications</h2>
 *
 * @author macintoshhd
 * createdAt : 2019-07-06 09:26
 * <p>
 * Description:
 */
public class FilterSpecifications<T> implements Specification<T> {


    private List<FilterCondition> filterAndConditions;
    private List<FilterCondition> filterOrConditions;


    public FilterSpecifications() {
        filterAndConditions = new ArrayList<>();
        filterOrConditions = new ArrayList<>();
    }


    public void addCondition(List<FilterCondition> lstAndFieldConditions, List<FilterCondition> lstOrFieldConditions) {

        if (lstAndFieldConditions != null && !lstAndFieldConditions.isEmpty()) {
            filterAndConditions.addAll(lstAndFieldConditions);
        }
        if (lstOrFieldConditions != null && !lstOrFieldConditions.isEmpty()) {
            filterOrConditions.addAll(lstOrFieldConditions);
        }
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicatesAndClause = new ArrayList<>();
        List<Predicate> predicatesOrClause = new ArrayList<>();

        filterAndConditions.forEach(condition -> predicatesAndClause.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        filterOrConditions.forEach(condition -> predicatesOrClause.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));


        if (!predicatesAndClause.isEmpty() && !predicatesOrClause.isEmpty()) {
            return criteriaBuilder.and(criteriaBuilder.and(predicatesAndClause.toArray(new Predicate[predicatesAndClause.size()])),
                    criteriaBuilder.or(criteriaBuilder.or(predicatesOrClause.toArray(new Predicate[predicatesOrClause.size()]))));
        } else if (!predicatesAndClause.isEmpty()) {
            return criteriaBuilder.and(predicatesAndClause.toArray(new Predicate[predicatesAndClause.size()]));

        } else if (!predicatesOrClause.isEmpty()) {
            return criteriaBuilder.or(predicatesOrClause.toArray(new Predicate[predicatesOrClause.size()]));

        } else {
            return null;
        }

    }


    private Predicate buildPredicate(FilterCondition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate p;

        switch (condition.getOperator()) {
            case EQUAL:
                p = criteriaBuilder.equal(root.get(condition.getField()), condition.getValue());
                break;
            case NOT_EQUAL:
                p = criteriaBuilder.notEqual(root.get(condition.getField()), condition.getValue());
                break;
             /*
                Operator for any type
             */
            case IS_NULL:
                p = criteriaBuilder.isNull(root.get(condition.getField()));
                break;
            case IS_NOT_NULL:
                p = criteriaBuilder.isNotNull(root.get(condition.getField()));
                break;
            /*
                Operator for String type
             */
            case IS_EMPTY:
                p = criteriaBuilder.equal(root.get(condition.getField()), "");
                break;
            case IS_NOT_EMPTY:
                p = criteriaBuilder.notEqual(root.get(condition.getField()), "");
                break;
            case CONTAINS:
                p = criteriaBuilder.like(root.get(condition.getField()).as(String.class), "%" + condition.getValue() + "%");
                break;
            case NOT_CONTAINS:
                p = criteriaBuilder.notLike(root.get(condition.getField()).as(String.class), "%" + condition.getValue() + "%");
                break;
            case START_WITH:
                p = criteriaBuilder.like(root.get(condition.getField()).as(String.class), condition.getValue() + "%");
                break;
            case END_WITH:
                p = criteriaBuilder.like(root.get(condition.getField()).as(String.class), "%" + condition.getValue());
                break;
            case GREATER_THAN:
                p = criteriaBuilder.greaterThan(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                p = criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case LESS_THAN:
                p = criteriaBuilder.lessThan(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case LESSTHAN_OR_EQUAL_TO:
                p = criteriaBuilder.lessThanOrEqualTo(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case JOIN:
                p = criteriaBuilder.equal(root.join(condition.getField()).get("id"), UUID.fromString((String) condition.getValue()));
                break;
            default:
                p = criteriaBuilder.equal(root.get(condition.getField()), condition.getValue());
        }
        return p;
    }


    private static LocalDateTime stringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
    }
}
