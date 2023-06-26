package tn.corilus.authentification.authentification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.Support;
import tn.corilus.authentification.authentification.repository.SupportRepository;

@ContextConfiguration(classes = {SupportServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SupportServiceImplTest {
    @MockBean
    private SupportRepository supportRepository;

    @Autowired
    private SupportServiceImpl supportServiceImpl;

    /**
     * Method under test: {@link SupportServiceImpl#getSupport(Long)}
     */
    @Test
    void testGetSupport() {
        // Arrange
        Support support = new Support();
        when(supportRepository.findById((Long) any())).thenReturn(Optional.of(support));

        // Act and Assert
        assertSame(support, supportServiceImpl.getSupport(123L));
        verify(supportRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link SupportServiceImpl#addPicture(Long, MultipartFile)}
     */
    @Test
    void testAddPicture3() throws IOException {
        // Arrange
        when(supportRepository.findById((Long) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertEquals("Doctor not found", supportServiceImpl.addPicture(1L,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(supportRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link SupportServiceImpl#modifySupport(Support)}
     */
    @Test
    void testModifySupport() {
        // Arrange
        Support support = new Support();
        when(supportRepository.save((Support) any())).thenReturn(support);
        when(supportRepository.findById((Long) any())).thenReturn(Optional.of(new Support()));

        // Act and Assert
        assertSame(support, supportServiceImpl.modifySupport(new Support()));
        verify(supportRepository).save((Support) any());
        verify(supportRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link SupportServiceImpl#getAllSupports()}
     */
    @Test
    void testGetAllSupports() {
        // Arrange
        ArrayList<Support> supportList = new ArrayList<>();
        when(supportRepository.findAll()).thenReturn(supportList);

        // Act
        List<Support> actualAllSupports = supportServiceImpl.getAllSupports();

        // Assert
        assertSame(supportList, actualAllSupports);
        assertTrue(actualAllSupports.isEmpty());
        verify(supportRepository).findAll();
    }

    /**
     * Method under test: {@link SupportServiceImpl#getSupportWithLeastConversations()}
     */
    @Test
    void testGetSupportWithLeastConversations() {
        // Arrange
        Support support = new Support();
        when(supportRepository.findSupportWithLeastConversations()).thenReturn(support);

        // Act and Assert
        assertSame(support, supportServiceImpl.getSupportWithLeastConversations());
        verify(supportRepository).findSupportWithLeastConversations();
    }
}

