package DTO;

public class NhaXuatBanDTO {
    private int maNXB;
    private String tenNXB;
    private String diaChi;
    private String sdt;

    public NhaXuatBanDTO() {
    }

    public NhaXuatBanDTO(int maNXB, String tenNXB, String diaChi, String sdt) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public int getMaNXB() { return maNXB; }
    public void setMaNXB(int maNXB) { this.maNXB = maNXB; }

    public String getTenNXB() { return tenNXB; }
    public void setTenNXB(String tenNXB) { this.tenNXB = tenNXB; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
}