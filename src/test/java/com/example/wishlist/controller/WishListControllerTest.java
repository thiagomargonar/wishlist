package com.example.wishlist.controller;

import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.WishlistDTO;
import com.example.wishlist.service.WishListService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@AutoConfigureWebTestClient
@WebFluxTest(WishListController.class)
class WishListControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private WishListService wishListService;

    @Test
    void when_getWishList_then_return_Person_values_and_status_ok() {
        var person = PersonDTO.builder()
                .withDataNascimento(LocalDate.now())
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishList())
                .build();

        when(wishListService.getWishListByPersonDocument(person.getDocument())).thenReturn(Mono.just(person));

        client.get()
                .uri("/wishList/{document}", "12345678901")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.document").isEqualTo(person.getDocument())
                .jsonPath("$.name").isEqualTo(person.getName())
                .jsonPath("$.wishList").isArray()
                .jsonPath("$.wishList[0].productName").isEqualTo(getWishList().get(0).getProductName())
                .jsonPath("$.wishList[0].value").isEqualTo(getWishList().get(0).getValue())
                .jsonPath("$.wishList[0].urlImage").isEqualTo(getWishList().get(0).getUrlImage());
    }

    @Test
    void when_getWishList_with_document_invalid_then_return_status_not_found() {

        when(wishListService.getWishListByPersonDocument("12345678900")).thenReturn(Mono.empty());

        client.get()
                .uri("/wishList/{document}", "12345678900")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void when_Post_WishList_with_document_bodyOk_then_return_PersonDTO() {

        var person = PersonDTO.builder()
                .withDataNascimento(LocalDate.of(2020, 9, 20))
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishList())
                .build();

        when(wishListService.saveWishList(any())).thenReturn(Mono.just(person));

        var testeste = toJSONObject(person).toString();

        client.post()
                .uri("/wishList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testeste)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.document").isEqualTo(person.getDocument())
                .jsonPath("$.name").isEqualTo(person.getName())
                .jsonPath("$.wishList").isArray()
                .jsonPath("$.wishList[0].productName").isEqualTo(getWishList().get(0).getProductName())
                .jsonPath("$.wishList[0].value").isEqualTo(getWishList().get(0).getValue())
                .jsonPath("$.wishList[0].urlImage").isEqualTo(getWishList().get(0).getUrlImage());

        verify(wishListService).saveWishList(any());
    }

    @Test
    void when_Post_WishList_with_document_isNull_in_body_then_return_BadRequest() {

        var person = PersonDTO.builder()
                .withDataNascimento(LocalDate.of(2020, 9, 20))
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishList())
                .build();

        when(wishListService.saveWishList(any())).thenReturn(Mono.just(person));

        var testeste = toJSONObject(person).toString().replace("document", "");

        client.post()
                .uri("/wishList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testeste)
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(wishListService, never()).saveWishList(any());
    }

    @Test
    void when_Put_WishList_with_document_bodyOk_then_return_PersonDTO() {
        var person = PersonDTO.builder()
                .withDataNascimento(LocalDate.of(2020, 9, 20))
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishList())
                .build();

        when(wishListService.updateWishList(any())).thenReturn(Mono.just(person));

        var testeste = toJSONObject(person).toString();

        client.put()
                .uri("/wishList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testeste)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.document").isEqualTo(person.getDocument())
                .jsonPath("$.name").isEqualTo(person.getName())
                .jsonPath("$.wishList").isArray()
                .jsonPath("$.wishList[0].productName").isEqualTo(getWishList().get(0).getProductName())
                .jsonPath("$.wishList[0].value").isEqualTo(getWishList().get(0).getValue())
                .jsonPath("$.wishList[0].urlImage").isEqualTo(getWishList().get(0).getUrlImage());

        verify(wishListService).updateWishList(any());
    }

    @Test
    void when_Put_WishList_with_name_isNull_then_return_BadRequest() {
        var person = PersonDTO.builder()
                .withDataNascimento(LocalDate.of(2020, 9, 20))
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishList())
                .build();

        var testeste = toJSONObject(person).toString().replace("name", "");

        client.put()
                .uri("/wishList")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testeste)
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(wishListService, never()).updateWishList(any());
    }


    @Test
    void when_deleteWishList_with_idPerson_then_return_status_ok() {

        when(wishListService.deleteWishListByPersonId("123456")).thenReturn(Mono.empty());

        client.delete()
                .uri("/wishList/{document}", "123456")
                .exchange()
                .expectStatus()
                .isOk();

        verify(wishListService).deleteWishListByPersonId(any());
    }


    public static JSONObject toJSONObject(PersonDTO request) {
        try {
            return new JSONObject()
                    .put("name", request.getName())
                    .put("document", request.getDocument())
                    .put("birthDate", "20-09-2020")
                    .put("wishList", makeWishListJSON(request.getWishList()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONArray makeWishListJSON(List<WishlistDTO> wishList) {
        var arrayJson = new JSONArray();
        wishList.forEach(dto -> {
            try {
                var item = new JSONObject();
                item.put("value", dto.getValue());
                item.put("productName", dto.getProductName());
                item.put("urlImage", dto.getUrlImage());
                arrayJson.put(item);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return arrayJson;
    }

    private List<WishlistDTO> getWishList() {
        return List.of(WishlistDTO.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("produto de teste")
                .withUrlImage("/teste/123")
                .build());
    }

}