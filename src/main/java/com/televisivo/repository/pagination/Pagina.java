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
    private List<PaginaItem> items;
    private int numeroAtual;

    public Pagina(Page<T> page, int pageSize, HttpServletRequest httpServletRequest) {
        this.page = page;
        String httpUrl = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "").toString().replaceAll("\\+", "%20");
        this.uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
        this.pageSize = pageSize;

        items = new ArrayList<>();
        numeroAtual = page.getNumber() + 1;

        int start;
        int tamanho;
        
        if (page.getTotalPages() <= pageSize) {
            start = 1;
            tamanho = page.getTotalPages();
        } else {
            if (numeroAtual <= pageSize - pageSize / 2) {
                start = 1;
                tamanho = pageSize;
            } else if (numeroAtual >= page.getTotalPages() - pageSize / 2) {
                start = page.getTotalPages() - pageSize + 1;
                tamanho = pageSize;
            } else {
                start = numeroAtual - pageSize / 2;
                tamanho = pageSize;
            }
        }
        for (int i = 0; i < tamanho; i++) {
            items.add(new PaginaItem(start + i, (start + i) == numeroAtual));
        }
    }

    public List<PaginaItem> getItems() {
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

    public int getNumero() {
        return numeroAtual;
    }

    public int getPageSize() {
        return pageSize;
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

    public int getTamanho() {
        return page.getSize();
    }

    public boolean isAnterior() {
        return page.hasPrevious();
    }

    public boolean isProxima() {
        return page.hasNext();
    }

    public String urlPagina(int tamanho, int pagina, String sort, String dir) {
        return uriComponentsBuilder.replaceQueryParam("pagina", pagina, "tamanho", tamanho, "sort", sort, "dir", dir).build(true).encode().toUriString();
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

    public boolean descendente(String propriedade) {
        return inverterDirecao(propriedade).equals("asc");
    }

    public boolean ordenada(String propriedade) {
		Order order = page.getSort().getOrderFor(propriedade); 
		if (order == null) {
			return false;
		}
		return page.getSort().getOrderFor(propriedade) != null;
	}
}