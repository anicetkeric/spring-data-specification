package com.aek.springdataspecification.helpers.filter;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class FilterSpecifications<T> implements Specification<T>{



    private List<FilterCondition> conditions;


    public FilterSpecifications() {
        conditions = new ArrayList<>();
    }

    public void addCondition(List<FilterCondition> lstConditions) {
        if(!lstConditions.isEmpty()){
            conditions.addAll(lstConditions);
        }
    }


    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
       List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    public Predicate buildPredicate(FilterCondition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.getOperator()) {
            case EQUAL:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case GREATER_THAN:
                break;
            case JOIN:
                return buildEqualsJoinByPrimaryKeyPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            default:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
        }
        throw new RuntimeException();
    }

    private Predicate buildEqualsPredicateToCriteria(FilterCondition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.getField()), condition.getValue());
    }
    private Predicate buildEqualsJoinByPrimaryKeyPredicateToCriteria(FilterCondition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.join(condition.getField()).get("id"), UUID.fromString((String)condition.getValue()) );
    }



}
