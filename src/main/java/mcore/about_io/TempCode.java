package mcore.about_io;

/**
 * 代码片段信息
 * @author Qian
 */
public class TempCode {
	
	private String codeType; 		// 代码类型
	private String importTemp;		// 导入类列表
	private String codeTemp;		// 代码片段
	private String sourcePath;		// 源码路径
	private String tempName;		// 模板名称
	
	public TempCode(){};
	
	public TempCode(String sourcePath,
			String tempName,
			String codeType,
			String codeTemp,
			String importTemp){
		this.sourcePath = sourcePath;
		this.tempName = tempName;
		this.codeType = codeType;
		this.codeTemp = codeTemp;
		this.importTemp = importTemp;
	}
	
	public String getTempName() {
		return tempName;
	}
	public String getCodeType() {
		return codeType;
	}
	public String getImportTemp() {
		return importTemp;
	}
	public String getCodeTemp() {
		return codeTemp;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public void setImportTemp(String importTemp) {
		this.importTemp = importTemp;
	}
	public void setCodeTemp(String codeTemp) {
		this.codeTemp = codeTemp;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	@Override
	public String toString() {
		return "[tempName="+tempName+" codeType="+codeType
				+" sourcePath="+sourcePath+"]";
	}
	@Override
	public int hashCode() {
		return (toString()+importTemp+codeTemp).hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null||!(obj instanceof TempCode))
			return false;
		TempCode temp = (TempCode) obj;
		String currCont = toString()+importTemp+codeTemp;
		String objCont = temp.toString()+temp.getImportTemp()+temp.getCodeTemp();
		return currCont.equals(objCont);
	}
}
