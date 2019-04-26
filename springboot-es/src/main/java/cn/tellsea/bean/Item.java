package cn.tellsea.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
public class Item {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Keyword)
    private String category;// 分类

    @Field(type = FieldType.Keyword)
    private String brand; // 品牌

    @Field(type = FieldType.Double)
    private Double price; // 价格

    @Field(index = false, type = FieldType.Keyword)
    private String images; // 图片地址
}
/**
 * Spring Data通过注解来声明字段的映射属性，有下面的三个注解：
 *
 * - `@Document` 作用在类，标记实体类为文档对象，一般有两个属性
 *      - indexName：对应索引库名称
 *      - type：对应在索引库中的类型
 *      - shards：分片数量，默认5
 *      - replicas：副本数量，默认1
 * - `@Id` 作用在成员变量，标记一个字段作为id主键
 * - `@Field` 作用在成员变量，标记为文档的字段，并指定字段映射属性：
 *      - type：字段类型，是是枚举：FieldType
 *      - index：是否索引，布尔类型，默认是true
 *      - store：是否存储，布尔类型，默认是false
 *      - analyzer：分词器名称
 * */