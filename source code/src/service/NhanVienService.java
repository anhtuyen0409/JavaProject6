package service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import model.HienThiToanBoDonDatHangModel;
import model.HienThiToanBoNhanVienModel;
import model.NhanVienModel;
import model.SanPhamModel;


public class NhanVienService extends SQLServerService{
	public int themNhanVien(NhanVienModel nv)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String sql="insert into NhanVien values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, nv.getTenNV());
			preStatement.setString(2, nv.getNamSinh());
			preStatement.setString(3, nv.getDiaChi());
			preStatement.setString(4, nv.getSDT());
			preStatement.setString(5, nv.getEmail());
			preStatement.setString(6, nv.getNgayVaoLamViec());
			preStatement.setInt(7, 0);
			preStatement.setInt(8, nv.getMaLoaiNV());
			preStatement.setString(9, nv.getTenDangNhap());
			return preStatement.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return -1;
	}
	public int suaNhanVien(int ma, String tenMoi, String namSinhMoi, String diaChiMoi, String SDTMoi, String emailMoi, String ngayVaoLamViecMoi){
		try
		{
			String sql="update NhanVien set TenNV=?, NamSinh=?, DiaChi=?, SDT=?, Email=?, NgayVaoLamViec=? where MaNV=?";
			PreparedStatement preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, tenMoi);
			preStatement.setString(2, namSinhMoi);
			preStatement.setString(3, diaChiMoi);
			preStatement.setString(4, SDTMoi);
			preStatement.setString(5, emailMoi);
			preStatement.setString(6, ngayVaoLamViecMoi);
			preStatement.setInt(7, ma);
			return preStatement.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return -1;
	}
	public int xoaNhanVien(int ma){
		try
		{
			String sql="update NhanVien set DaXoa=? where MaNV=?";
			PreparedStatement preStatement=conn.prepareStatement(sql);
			preStatement.setInt(1, 1);
			preStatement.setInt(2, ma);
			return preStatement.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return -1;
	}
	public Vector<NhanVienModel>timNhanVienTheoTen(String tenNV)
	{
		Vector<NhanVienModel> vecnv = new Vector<NhanVienModel>();
		try
		{
			String sql="select * from NhanVien where TenNV=?";
			PreparedStatement preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, tenNV);
			ResultSet result=preStatement.executeQuery();
			while(result.next())
			{
				NhanVienModel nv = new NhanVienModel();
				nv.setMaNV(result.getInt(1));
				nv.setTenNV(result.getString(2));
				nv.setNamSinh(result.getString(3));
				nv.setDiaChi(result.getString(4));
				nv.setSDT(result.getString(5));
				nv.setEmail(result.getString(6));
				nv.setNgayVaoLamViec(result.getString(7));
				//b???ng c?? c???t n??o th?? thao t??c c???t t????ng ???ng trong database
				nv.setMaLoaiNV(result.getInt(9));
				nv.setTenDangNhap(result.getString(10));
				vecnv.add(nv);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return vecnv; //tr??? v??? danh s??ch t??m ki???m
	}
	public Vector<HienThiToanBoNhanVienModel> docToanBoNhanVien()
	{
		Vector<HienThiToanBoNhanVienModel>dsNV = new Vector<HienThiToanBoNhanVienModel>();
		try
		{
			CallableStatement callStatement = conn.prepareCall("{call HienThiToanBoNhanVien}");
			ResultSet rs = callStatement.executeQuery();
			while(rs.next())
			{
				HienThiToanBoNhanVienModel nv = new HienThiToanBoNhanVienModel();
				nv.setMaNV(rs.getInt(1));
				nv.setTenNV(rs.getString(2));
				nv.setNamSinh(rs.getString(3));
				nv.setDiaChi(rs.getString(4));
				nv.setSDT(rs.getString(5));
				nv.setEmail(rs.getString(6));
				nv.setNgayVaoLamViec(rs.getString(7));
				nv.setTenBoPhan(rs.getString(8));
				nv.setTaiKhoan(rs.getString(9));
				dsNV.add(nv);
			}		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsNV;
	}
	public Vector<HienThiToanBoNhanVienModel> docNhanVienTheoLoai(int maLoai)
	{
		Vector<HienThiToanBoNhanVienModel>dsNV = new Vector<HienThiToanBoNhanVienModel>();
		try
		{
			CallableStatement callStatement = conn.prepareCall("{call HienThiToanBoNhanVienTheoBoPhan(?)}");
			callStatement.setInt(1, maLoai);
			ResultSet rs = callStatement.executeQuery();
			while(rs.next())
			{
				HienThiToanBoNhanVienModel nv = new HienThiToanBoNhanVienModel();
				nv.setMaNV(rs.getInt(1));
				nv.setTenNV(rs.getString(2));
				nv.setNamSinh(rs.getString(3));
				nv.setDiaChi(rs.getString(4));
				nv.setSDT(rs.getString(5));
				nv.setEmail(rs.getString(6));
				nv.setNgayVaoLamViec(rs.getString(7));
				nv.setTenBoPhan(rs.getString(8));
				nv.setTaiKhoan(rs.getString(9));
				dsNV.add(nv);
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsNV;
	}
	public Vector<HienThiToanBoNhanVienModel> docNhanVienTheoTenDangNhap(String tenDangNhap)
	{
		Vector<HienThiToanBoNhanVienModel>dsNV = new Vector<HienThiToanBoNhanVienModel>();
		try
		{
			CallableStatement callStatement = conn.prepareCall("{call HienThiToanBoNhanVienTheoTaiKhoan(?)}");
			callStatement.setString(1, tenDangNhap);
			ResultSet rs = callStatement.executeQuery();
			while(rs.next())
			{
				HienThiToanBoNhanVienModel nv = new HienThiToanBoNhanVienModel();
				nv.setMaNV(rs.getInt(1));
				nv.setTenNV(rs.getString(2));
				nv.setNamSinh(rs.getString(3));
				nv.setDiaChi(rs.getString(4));
				nv.setSDT(rs.getString(5));
				nv.setEmail(rs.getString(6));
				nv.setNgayVaoLamViec(rs.getString(7));
				nv.setTenBoPhan(rs.getString(8));
				nv.setTaiKhoan(rs.getString(9));
				dsNV.add(nv);
			}	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsNV;
	}
	public Vector<NhanVienModel> docToanBoNhanVienQLKho()
	{
		Vector<NhanVienModel>dsNV = new Vector<NhanVienModel>();
		try
		{
			String sql="select * from NhanVien where MaLoaiNV=1 and DaXoa=0";
			PreparedStatement preStatement=conn.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			while(result.next())
			{
				NhanVienModel nv = new NhanVienModel();
				nv.setMaNV(result.getInt(1));
				nv.setTenNV(result.getString(2));
				nv.setNamSinh(result.getString(3));
				nv.setDiaChi(result.getString(4));
				nv.setSDT(result.getString(5));
				nv.setEmail(result.getString(6));
				nv.setNgayVaoLamViec(result.getString(7));
				nv.setDaXoa(0);
				nv.setMaLoaiNV(result.getInt(9));
				nv.setTenDangNhap(result.getString(10));
				dsNV.add(nv);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dsNV;
	}
	
}
