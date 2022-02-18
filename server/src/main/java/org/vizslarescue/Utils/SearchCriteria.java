package org.vizslarescue.Utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SearchCriteria {
    private final String key;
    private final SearchOperation searchOperation;
    private final boolean isOrOperation;
    private final String arg;

    public static SearchCriteria create(String key, SearchOperation searchOperation, boolean isOrOperation, String arg)
    {
        return new SearchCriteria(key, searchOperation, isOrOperation, arg);
    }
}
