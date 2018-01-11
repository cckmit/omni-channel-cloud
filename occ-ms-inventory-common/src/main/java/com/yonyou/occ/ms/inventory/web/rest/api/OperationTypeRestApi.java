package com.yonyou.occ.ms.inventory.web.rest.api;

import java.util.List;

import com.yonyou.occ.ms.inventory.service.dto.OperationTypeDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The REST API for OperationType.
 *
 * @author WangRui
 * @date 2018-01-08 11:27:16
 */
public interface OperationTypeRestApi {
    /**
     * GET  /operation-types : get all the operationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operationTypes in body
     */
    @GetMapping("/operation-types")
    ResponseEntity<List<OperationTypeDTO>> getAllOperationTypes(Pageable pageable);

    /**
     * GET  /operation-types/:id : get the "id" operationType.
     *
     * @param id the id of the operationTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationTypeDTO, or with status 404 (Not
     * Found)
     */
    @GetMapping("/operation-types/{id}")
    ResponseEntity<OperationTypeDTO> getOperationType(@PathVariable("id") String id);
}
