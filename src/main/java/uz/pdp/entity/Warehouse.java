package uz.pdp.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.template.AbsEntity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Warehouse extends AbsEntity {

}
