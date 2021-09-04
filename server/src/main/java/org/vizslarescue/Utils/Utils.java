package org.vizslarescue.Utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;

public final class Utils {

    public static PageRequest getPager(int pageSize, int page, List<SortKey> sortKeys) {
        PageRequest pager;

        if(sortKeys != null && sortKeys.size() > 0) {
            pager = PageRequest.of(page, pageSize, Utils.getSort(sortKeys));
        } else {
            pager = PageRequest.of(page, pageSize);
        }

        return pager;
    }

    public static Sort getSort(List<SortKey> sortKeys) {
        List<Order> orders = new ArrayList<Order>();

        for(SortKey sortKey : sortKeys) {
            if(sortKey.getDirection().equals("desc")) {
                orders.add(new Order(Direction.DESC, sortKey.getField()));
            } else {
                orders.add(new Order(Direction.ASC, sortKey.getField()));
            }
        }
        
        return Sort.by(orders);
    }

    public static Query getQuery(List<FilterKey> filters) {
        Query query = new Query();

        if(filters != null && filters.size() > 0) {
            for(FilterKey filter : filters) {
                query.addCriteria(filter.getCriteria());
            }
        }

        return query;
    }

    public static List<SortKey> mapSortKeys(List<String> sortStrings) {
        List<SortKey> sortKeys = new ArrayList<SortKey>();

        for(String sort : sortStrings) {
            sortKeys.add(SortKey.fromString(sort));
        }

        return sortKeys;
    }

    public static List<FilterKey> mapFilterKeys(List<String> filterStrings) {
        List<FilterKey> filterKeys = new ArrayList<FilterKey>();

        for(String filter : filterStrings) {
            filterKeys.add(FilterKey.fromString(filter));
        }

        return filterKeys;
    }
}
