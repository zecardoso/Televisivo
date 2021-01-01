package com.televisivo.repository.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class Pagina<T> {

    private Page<T> page;
    private UriComponentsBuilder uriComponentsBuilder;
    private int pageSize;
    private List<PageItem> items;
    private int currentNumber;

    public Pagina(Page<T> page, int pageSize, HttpServletRequest httpServletRequest) {
        this.page = page;
        String httpUrl = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "").toString().replace("\\+", "%20");
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
        this.pageSize = pageSize;

        items = new ArrayList<>();
        currentNumber = page.getNumber() + 1;

        int start;
        int size;

        if (page.getTotalPages() <= pageSize) {
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= pageSize - pageSize / 2) {
                start = 1;
                size = pageSize;
            } else if (currentNumber >= page.getTotalPages() - pageSize / 2) {
                start = page.getTotalPages() - pageSize + 1;
                size = pageSize;
            } else {
                start = currentNumber - pageSize / 2;
                size = pageSize;
            }
        }
        for (int i = 0; i < size; i++) {
            items.add(new PageItem(start + i, (start + i) == currentNumber));
        }
    }

	public List<PageItem> getItems() {
		return items;
	}

	public List<T> getConteudo() {
		return page.getContent();
	}

	public boolean isVazia() {
		return page.getContent().isEmpty();
	}

	public int getAtual() {
		return page.getNumber();
	}

	public int getNumber() {
		return currentNumber;
	}

	public boolean isPrimeira() {
		return page.isFirst();
	}

	public boolean isUltima() {
		return page.isLast();
	}

	public int getTotal() {
		return page.getTotalPages();
	}

	public long getTotalElementos() {
		return page.getTotalElements();
	}

	public int getSize() {
		return page.getSize();
	}

	public boolean isFirstPage() {
		return page.isFirst();
	}

	public boolean isLastPage() {
		return page.isLast();
	}

	public boolean isHasPreviousPage() {
		return page.hasPrevious();
	}

	public boolean isHasNextPage() {
		return page.hasNext();
	}

	public int getPageSize() {
		return pageSize;
	}

    public String urlPagina(int size, int pagina) {
		return uriComponentsBuilder.replaceQueryParam("page", pagina, "size", size).build(true).encode().toUriString();
	}

	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriComponentsBuilderOrder = UriComponentsBuilder.fromUriString(uriComponentsBuilder.build(true).encode().toUriString());
        String valorSort = String.format("%s", propriedade);
        String valorDir = String.format("%s", inverterDirecao(propriedade));
        return uriComponentsBuilderOrder.replaceQueryParam("sort", valorSort).replaceQueryParam("dir", valorDir).build(true).encode().toUriString();
    }

    public String inverterDirecao(String propriedade) {
        String direcao = "asc";
        Order order = page.getSort().getOrderFor(propriedade);
        if (order != null) {
            direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
        }
        return direcao;
    }

	public class PageItem {
		private int number;
		private boolean current;

		public PageItem(int number, boolean current) {
			this.number = number;
			this.current = current;
		}

		public int getNumber() {
			return this.number;
		}

		public boolean isCurrent() {
			return this.current;
		}
	}
}