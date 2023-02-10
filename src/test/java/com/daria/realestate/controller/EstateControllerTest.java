package com.daria.realestate.controller;

import com.daria.realestate.domain.Estate;
import com.daria.realestate.domain.enums.AcquisitionStatus;
import com.daria.realestate.domain.enums.PaymentTransactionType;
import com.daria.realestate.domain.enums.TypeOfEstate;
import com.daria.realestate.dto.EstateDTO;
import com.daria.realestate.dto.EstateSearchFilter;
import com.daria.realestate.dto.Page;
import com.daria.realestate.service.impl.EstateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(EstateController.class)
public class EstateControllerTest {

    @MockBean
    private EstateServiceImpl estateService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllEstateDetails() throws Exception {
        given(estateService.getAllDetailsOfAnEstate(1L)).willReturn(getEstateDTO());

        this.mockMvc.perform(get("/api/v1/estate/allDetails/{estateId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullAddress").isNotEmpty());
    }

    @Test
    public void createEstate() throws Exception {
        given(estateService.createEstate(getEstateDTO())).willReturn(getEstateDTO());

        this.mockMvc.perform(post("/api/v1/estate/")

                        .content("{\n" +
                                "   \"paymentTransactionType\" : \"RENT\",\n" +
                                "    \"acquisitionStatus\" : \"ON_HOLD\",\n" +
                                "    \"createdAt\" :\"2017-05-15T15:12:59.152\", \n" +
                                "    \"lastUpdatedAt\" : \"2017-05-15T15:12:59.152\",\n" +
                                "    \"squareMeters\" :25,\n" +
                                "    \"numberOfRooms\" : 2,\n" +
                                "    \"numberOfBathRooms\" : 1,\n" +
                                "    \"numberOfGarages\" : 1,\n" +
                                "    \"yearOfConstruction\" :\"2017-05-15\",\n" +
                                "    \"typeOfEstate\" : \"BUNGALOW\",\n" +
                                "    \"fullAddress\" :\"fullAddress\",\n" +
                                "    \"city\" : \"city\",\n" +
                                "    \"country\" : \"country\",\n" +
                                "    \"email\" : \"email\",\n" +
                                "    \"price\" : 10000,\n" +
                                "    \"lastPriceUpdatedAt\" : \"2017-05-15T15:12:59.152\",\n" +
                                "    \"currency\" : \"EUR\"\n" +
                                "}")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().methodName("createEstate"));
    }

    @Test
    public void getAllEstatesByAllCriteria() throws Exception {
    //  given(estateService.getEstatesFilteredByAllEstateCriteria(getEstateSearchFilter(), 5, 1)).willReturn(getEstateList(5));

        this.mockMvc.perform(post("/api/v1/estate/estatesByAllCriteria?page=1&size=5")
                .content(asJsonString(getEstateSearchFilter()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print());

//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.totalElements", is(5)))
//                .andExpect(jsonPath("$.currentPage", is(1)))
//                .andExpect(jsonPath("$.elementsPerPage", is(5)))
//                .andExpect(handler().methodName("getAllEstatesByAllCriteria"));
    }

    private EstateDTO getEstateDTO() {
        return new EstateDTO(
                PaymentTransactionType.RENT,
                AcquisitionStatus.ON_HOLD,
                LocalDateTime.parse("2017-05-15T15:12:59.152"),
                LocalDateTime.parse("2017-05-15T15:12:59.152"),
                25,
                2,
                1,
                1,
                LocalDate.parse("2017-05-15"),
                TypeOfEstate.BUNGALOW,
                "fullAddress",
                "city",
                "country",
                "email",
                10000L,
                LocalDateTime.parse("2017-05-15T15:12:59.152"),
                "EUR"
        );
    }

    private Page<Estate> getEstateList(int totalElements) {
        List<Estate> estates = new ArrayList<>();

        estates.add(new Estate(PaymentTransactionType.RENT.toString(), AcquisitionStatus.ON_HOLD.toString(), LocalDateTime.now(), LocalDateTime.now()));
        estates.add(new Estate(PaymentTransactionType.RENT.toString(), AcquisitionStatus.ON_HOLD.toString(), LocalDateTime.now(), LocalDateTime.now()));
        estates.add(new Estate(PaymentTransactionType.RENT.toString(), AcquisitionStatus.ON_HOLD.toString(), LocalDateTime.now(), LocalDateTime.now()));
        estates.add(new Estate(PaymentTransactionType.RENT.toString(), AcquisitionStatus.ON_HOLD.toString(), LocalDateTime.now(), LocalDateTime.now()));
        estates.add(new Estate(PaymentTransactionType.RENT.toString(), AcquisitionStatus.ON_HOLD.toString(), LocalDateTime.now(), LocalDateTime.now()));

        return new Page<>(estates, totalElements, 1, 5);
    }

    private EstateSearchFilter getEstateSearchFilter() {
        return new EstateSearchFilter(
                AcquisitionStatus.ON_HOLD,
                PaymentTransactionType.RENT,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                "2023-01-01",
                "2023-01-01",
                TypeOfEstate.BARN,
                0, 0,
                "city", "country"
        );
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}