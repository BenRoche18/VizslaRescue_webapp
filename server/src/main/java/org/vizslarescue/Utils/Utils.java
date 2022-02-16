// package org.vizslarescue.Utils;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.domain.Sort.Order;
// import org.springframework.data.mongodb.core.aggregation.Aggregation;
// import org.springframework.data.mongodb.core.aggregation.MatchOperation;
// import org.springframework.data.mongodb.core.aggregation.SortOperation;
// import org.springframework.data.mongodb.core.query.Criteria;
// import org.springframework.data.mongodb.core.query.Query;

// public final class Utils {

//     public static PageRequest getPager(int pageSize, int page, List<SortKey> sortKeys) {
//         PageRequest pager;

//         if(sortKeys != null && sortKeys.size() > 0) {
//             pager = PageRequest.of(page, pageSize, Utils.getSort(sortKeys));
//         } else {
//             pager = PageRequest.of(page, pageSize);
//         }

//         return pager;
//     }
    
//     public static MatchOperation getMatchOperation(List<FilterKey> filters) {
//         if(filters.size() > 0) {
//             return Aggregation.match(Utils.getCriteria(filters));
//         }

//         return null;
//     }

//     public static Criteria getCriteria(List<FilterKey> filters) {
//         return new Criteria().andOperator(filters.stream().map(FilterKey::getCriteria).collect(Collectors.toList()));
//     }

//     public static List<SortKey> mapSortKeys(List<String> sortStrings) {
//         List<SortKey> sortKeys = new ArrayList<SortKey>();

//         for(String sort : sortStrings) {
//             sortKeys.add(SortKey.fromString(sort));
//         }

//         return sortKeys;
//     }

//     public static List<FilterKey> mapFilterKeys(List<String> filterStrings) {
//         List<FilterKey> filterKeys = new ArrayList<FilterKey>();

//         for(String filter : filterStrings) {
//             filterKeys.add(FilterKey.fromString(filter));
//         }

//         return filterKeys;
//     }
// }
