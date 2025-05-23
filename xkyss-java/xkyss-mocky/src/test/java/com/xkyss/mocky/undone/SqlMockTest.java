package com.xkyss.mocky.undone;

import com.xkyss.mocky.Mocky;
import com.xkyss.mocky.base.objects.Filler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SqlMockTest {

    @Test
    public void test_gen_product() {
        Mocky mocky = new Mocky();
        Filler<Product> builder = mocky.filler(Product::new)
            // 主键
            .setter(Product::setId, mocky.ids().uuid())
            // 产品品牌
            .setter(Product::setBrand, mocky.froms().from(Arrays.asList("华为", "美的", "小米")))
            // 产品类型
            .setter(Product::setType, mocky.strings())
            // 产品名称
            .setter(Product::setName, p ->
                p.getBrand() + mocky.strings().size(3).letters().get().toUpperCase() +
                "-" + mocky.strings().size(10).alphaNumeric().get().toUpperCase() + p.getType())
            // 产品价格
            .setter(Product::setPrice, () -> BigDecimal.valueOf(mocky.doubles().range(1, 10000).get()))
            // 产品数量
            .setter(Product::setStock, mocky.longs().range(1, 1000))
            // 秒杀价
            .setter(Product::setPriceSpike, () -> BigDecimal.valueOf(mocky.doubles().range(1, 8000).get()))
            // 秒杀时间
            .setter(Product::setSpikeTime, mocky.localDateTimes())
            // 创建时间
            .setter(Product::setCreateTime, mocky.localDateTimes())
            // 更新时间
            .setter(Product::setUpdateTime, mocky.localDateTimes())
            // 长
            .setter(Product::setRemark1, mocky.strings())
            // 宽
            .setter(Product::setRemark2, mocky.strings())
            // 高
            .setter(Product::setRemark3, mocky.strings())
            // 颜色
            .setter(Product::setRemark4, mocky.strings())
            // 重量
            .setter(Product::setRemark5, mocky.strings())
            // 描述
            .setter(Product::setRemark6, mocky.strings())
            // 折扣
            .setter(Product::setRemark7, mocky.strings())
            // 最低价
            .setter(Product::setRemark8, mocky.strings())
            // 最高价
            .setter(Product::setRemark9, mocky.strings())
            // 购买数
            .setter(Product::setRemark10, mocky.strings())
            // 退货率
            .setter(Product::setRemark11, mocky.strings());

        Product product = builder.get();
        System.out.println(product);
    }

    // @Data
    // @Table(name = "product")
    public class Product /*extends Model*/ {
        private String id;
        private String name;
        private String type;
        private String brand;
        private BigDecimal price;
        private Long stock;
        private BigDecimal priceSpike;
        // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime spikeTime;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        // @Column(name = "remark1")
        private String remark1;
        // @Column(name = "remark2")
        private String remark2;
        // @Column(name = "remark3")
        private String remark3;
        // @Column(name = "remark4")
        private String remark4;
        // @Column(name = "remark5")
        private String remark5;
        // @Column(name = "remark6")
        private String remark6;
        // @Column(name = "remark7")
        private String remark7;
        // @Column(name = "remark8")
        private String remark8;
        // @Column(name = "remark9")
        private String remark9;
        // @Column(name = "remark10")
        private String remark10;
        // @Column(name = "remark11")
        private String remark11;
        private Long totalRows;

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Long getStock() {
            return stock;
        }

        public void setStock(Long stock) {
            this.stock = stock;
        }

        public BigDecimal getPriceSpike() {
            return priceSpike;
        }

        public void setPriceSpike(BigDecimal priceSpike) {
            this.priceSpike = priceSpike;
        }

        public LocalDateTime getSpikeTime() {
            return spikeTime;
        }

        public void setSpikeTime(LocalDateTime spikeTime) {
            this.spikeTime = spikeTime;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getRemark3() {
            return remark3;
        }

        public void setRemark3(String remark3) {
            this.remark3 = remark3;
        }

        public String getRemark4() {
            return remark4;
        }

        public void setRemark4(String remark4) {
            this.remark4 = remark4;
        }

        public String getRemark5() {
            return remark5;
        }

        public void setRemark5(String remark5) {
            this.remark5 = remark5;
        }

        public String getRemark6() {
            return remark6;
        }

        public void setRemark6(String remark6) {
            this.remark6 = remark6;
        }

        public String getRemark7() {
            return remark7;
        }

        public void setRemark7(String remark7) {
            this.remark7 = remark7;
        }

        public String getRemark8() {
            return remark8;
        }

        public void setRemark8(String remark8) {
            this.remark8 = remark8;
        }

        public String getRemark9() {
            return remark9;
        }

        public void setRemark9(String remark9) {
            this.remark9 = remark9;
        }

        public String getRemark10() {
            return remark10;
        }

        public void setRemark10(String remark10) {
            this.remark10 = remark10;
        }

        public String getRemark11() {
            return remark11;
        }

        public void setRemark11(String remark11) {
            this.remark11 = remark11;
        }

        public Long getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(Long totalRows) {
            this.totalRows = totalRows;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", brand='" + brand + '\'' +
                    ", price=" + price +
                    ", stock=" + stock +
                    ", priceSpike=" + priceSpike +
                    ", spikeTime=" + spikeTime +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", remark1='" + remark1 + '\'' +
                    ", remark2='" + remark2 + '\'' +
                    ", remark3='" + remark3 + '\'' +
                    ", remark4='" + remark4 + '\'' +
                    ", remark5='" + remark5 + '\'' +
                    ", remark6='" + remark6 + '\'' +
                    ", remark7='" + remark7 + '\'' +
                    ", remark8='" + remark8 + '\'' +
                    ", remark9='" + remark9 + '\'' +
                    ", remark10='" + remark10 + '\'' +
                    ", remark11='" + remark11 + '\'' +
                    ", totalRows=" + totalRows +
                    '}';


        }
    }
}
