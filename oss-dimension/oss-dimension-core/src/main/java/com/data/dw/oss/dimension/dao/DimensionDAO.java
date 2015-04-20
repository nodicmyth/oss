package com.data.dw.oss.dimension.dao;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionDAO {

    Column save(DimensionRequestDTO dimensionInfo);

    Column find(DimensionRequestDTO dimensionInfo);
}
