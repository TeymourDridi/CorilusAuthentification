package tn.corilus.authentification.authentification.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.SupportRepository;
import tn.corilus.authentification.authentification.service.SupportServiceImpl;

class SupportControllerTest {
    /**
     * Method under test: {@link SupportController#getSupport()}
     */
    @Test
    void testGetSupport() {

        // Arrange
        SupportRepository supportRepository = mock(SupportRepository.class);
        when(supportRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Support>> actualSupport = (new SupportController(new SupportServiceImpl(supportRepository)))
                .getSupport();

        // Assert
        assertTrue(actualSupport.hasBody());
        assertEquals(200, actualSupport.getStatusCodeValue());
        assertTrue(actualSupport.getHeaders().isEmpty());
        verify(supportRepository).findAll();
    }


    /**
     * Method under test: {@link SupportController#getSupport(Long)}
     */
    @Test
    void testGetSupport3() {
        // Arrange
        SupportRepository supportRepository = mock(SupportRepository.class);
        when(supportRepository.findById((Long) any())).thenReturn(Optional.of(new Support()));

        // Act
        ResponseEntity<Support> actualSupport = (new SupportController(new SupportServiceImpl(supportRepository)))
                .getSupport(123L);

        // Assert
        assertTrue(actualSupport.hasBody());
        assertTrue(actualSupport.getHeaders().isEmpty());
        assertEquals(200, actualSupport.getStatusCodeValue());
        verify(supportRepository).findById((Long) any());
    }



    /**
     * Method under test: {@link SupportController#getSupport(Support)}
     */
    @Test
    void testGetSupport5() {
        // Arrange
        SupportRepository supportRepository = mock(SupportRepository.class);
        when(supportRepository.save((Support) any())).thenReturn(new Support());
        when(supportRepository.findById((Long) any())).thenReturn(Optional.of(new Support()));
        SupportController supportController = new SupportController(new SupportServiceImpl(supportRepository));

        // Act
        ResponseEntity<Support> actualSupport = supportController.getSupport(new Support());

        // Assert
        assertTrue(actualSupport.hasBody());
        assertTrue(actualSupport.getHeaders().isEmpty());
        assertEquals(200, actualSupport.getStatusCodeValue());
        verify(supportRepository).save((Support) any());
        verify(supportRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link SupportController#getImageWithMediaType(String)}
     */
    @Test
    void testGetImageWithMediaType() throws IOException {
        // Arrange and Act
        ResponseEntity<byte[]> actualImageWithMediaType = (new SupportController(
                new SupportServiceImpl(mock(SupportRepository.class)))).getImageWithMediaType("Picture Name");

        // Assert
        assertNull(actualImageWithMediaType.getBody());
        assertEquals(404, actualImageWithMediaType.getStatusCodeValue());
        assertTrue(actualImageWithMediaType.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link SupportController#getImageWithMediaType(String)}
     */
    @Test
    void testGetImageWithMediaType2() throws IOException {
        // Arrange and Act
        ResponseEntity<byte[]> actualImageWithMediaType = (new SupportController(
                new SupportServiceImpl(mock(SupportRepository.class)))).getImageWithMediaType("");

        // Assert
        assertNull(actualImageWithMediaType.getBody());
        assertEquals(404, actualImageWithMediaType.getStatusCodeValue());
        assertTrue(actualImageWithMediaType.getHeaders().isEmpty());
    }
}

