package com.skt.tsop.robot.controller.cctv;

import com.skt.tsop.robot.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * IndexController.
 *
 * @author yjkim@ntels.com
 */
@RestController
public class IndexController {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * index.
     *
     * @return ApiResponse
     */
    @GetMapping(value = "/")
    public ApiResponse index() {
        logger.debug("INDEX_CONTROLLER");
        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }
}
