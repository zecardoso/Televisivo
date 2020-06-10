package com.televisivo.repository.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginaItem {

    private int number;
    private boolean current;
}