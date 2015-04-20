package com.data.dw.oss.dimension.core.dao;

import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionDAO {

    Column save(DimensionRequestDTO dimensionInfo);

    Column find(DimensionRequestDTO dimensionInfo);
}
