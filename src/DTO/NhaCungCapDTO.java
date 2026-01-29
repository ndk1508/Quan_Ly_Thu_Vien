package DTO;

public class NhaCungCapDTO {
    private int maNCC;
    private String tenNCC;
    private String diaChi;

    public NhaCungCapDTO() {}

    public NhaCungCapDTO(int maNCC, String tenNCC, String diaChi) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
    }

    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}