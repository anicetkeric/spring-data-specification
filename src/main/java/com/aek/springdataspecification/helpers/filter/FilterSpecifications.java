package com.aek.springdataspecification.helpers.filter;

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
public class FilterSpecifications<T> implements Specification<T>{




    private List<FilterCondition> conditions = null;
    private FilterLogic filterLogic = FilterLogic.AND;


    public FilterSpecifications() {
        conditions = new ArrayList<>();
    }



    public void addCondition(List<FilterCondition> lstConditions, FilterLogic logic) {
        filterLogic = logic;
        if(!lstConditions.isEmpty()){
            conditions.addAll(lstConditions);
        }
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);

        if(predicates.size() > 1){

            if(filterLogic.equals(FilterLogic.AND)){
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }else{
              return  criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            }

        }else{
            return predicates.get(0);
        }
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
       List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
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
                p = criteriaBuilder.like(root.get(condition.getField()).as(String.class), "%" +  condition.getValue() + "%");
                break;
             case NOT_CONTAINS:
                p = criteriaBuilder.notLike(root.get(condition.getField()).as(String.class), "%" + condition.getValue() + "%");
                break;
           case START_WITH:
               p = criteriaBuilder.like(root.get(condition.getField()).as(String.class),  condition.getValue() + "%");
                break;
            case END_WITH:
                p = criteriaBuilder.like(root.get(condition.getField()).as(String.class), "%" + condition.getValue());
                break;
            case GREATER_THAN:
                p = criteriaBuilder.greaterThan(root.get(condition.getField()).as(LocalDateTime.class),stringToLocalDateTime((String) condition.getValue()));
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                p = criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getField()).as(LocalDateTime.class),stringToLocalDateTime((String) condition.getValue()));
                break;
            case LESS_THAN:
                p = criteriaBuilder.lessThan(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case LESSTHAN_OR_EQUAL_TO:
                p = criteriaBuilder.lessThanOrEqualTo(root.get(condition.getField()).as(LocalDateTime.class), stringToLocalDateTime((String) condition.getValue()));
                break;
            case JOIN:
                p =  criteriaBuilder.equal(root.join(condition.getField()).get("id"), UUID.fromString((String)condition.getValue()) );
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
