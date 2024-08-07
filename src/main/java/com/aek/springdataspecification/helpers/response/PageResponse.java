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

package com.aek.springdataspecification.helpers.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

    private int totalPages;
    private long totalItems;
    private int currentPage;
    private int firstPage;
    private int lastPage;
    private boolean first;
    private boolean last;
    private int itemsPerPage;
    private int pageSize;

    private Sort sort;
    private List<T> items;

    public void setPageStats(Page<?> pg, List<T> elts) {
        first = pg.isFirst();
        last = pg.isLast();
        currentPage = pg.getNumber() + 1;
        pageSize = pg.getSize();
        totalPages = pg.getTotalPages();
        totalItems = pg.getTotalElements();
        sort = pg.getSort();
        firstPage = Math.max(1, pg.getNumber() - 5);
        lastPage = Math.min(this.firstPage + 10, pg.getTotalPages());
        itemsPerPage = pg.getNumberOfElements();
        items = elts;
    }


}
