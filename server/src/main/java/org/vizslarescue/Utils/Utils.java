package org.vizslarescue.Utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Query;

public final class Utils {
    
    public static PageRequest getPager(int pageSize, int page, List<String> sortKeys) {
        PageRequest pager;

        if(sortKeys != null && sortKeys.size() > 0) {
            List<SortKey> mappedSortKeys = new ArrayList<SortKey>();

            for(String sortKey : sortKeys) {
                mappedSortKeys.add(SortKey.fromString(sortKey));
            }

            pager = PageRequest.of(page, pageSize, Utils.getSort(mappedSortKeys));
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

    public static Query getQuery(List<String> filters) {
        Query query = new Query();

        if(filters != null && filters.size() > 0) {
            for(String filter : filters) {
                query.addCriteria(FilterKey.fromString(filter).getCriteria());
            }
        }

        return query;
    }
}
