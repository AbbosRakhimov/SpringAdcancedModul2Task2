package uz.pdp.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Currency  extends AbsEntity{

}
