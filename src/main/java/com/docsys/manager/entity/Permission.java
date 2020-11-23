package com.docsys.manager.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Permission {
    int permissionId;
    String permissionName;
    String permissionCode ;
    String menuName ;
    String menuCode ;
}
