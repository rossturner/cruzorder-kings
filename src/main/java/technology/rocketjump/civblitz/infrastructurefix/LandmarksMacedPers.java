package technology.rocketjump.civblitz.infrastructurefix;

public class LandmarksMacedPers implements StaticModFile {
	@Override
	public String getFilename() {
		return "ArtDefs/Landmarks_DLC_Maced_Pers.artdef";
	}

	@Override
	public String getFileContent() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
				"<AssetObjects..ArtDefSet>\n" +
				"\t<m_Version>\n" +
				"\t\t<major>4</major>\n" +
				"\t\t<minor>0</minor>\n" +
				"\t\t<build>318</build>\n" +
				"\t\t<revision>337</revision>\n" +
				"\t</m_Version>\n" +
				"\t<m_TemplateName text=\"Landmarks\"/>\n" +
				"\t<m_RootCollections>\n" +
				"\t\t<Element>\n" +
				"\t\t\t<m_CollectionName text=\"Landmarks\"/>\n" +
				"\t\t\t<m_ReplaceMergedCollectionElements>false</m_ReplaceMergedCollectionElements>\n" +
				"\t\t\t<Element>\n" +
				"\t\t\t\t<m_Fields>\n" +
				"\t\t\t\t\t<m_Values>\n" +
				"\t\t\t\t\t\t<Element class=\"AssetObjects..BoolValue\">\n" +
				"\t\t\t\t\t\t\t<m_bValue>true</m_bValue>\n" +
				"\t\t\t\t\t\t\t<m_ParamName text=\"FlattenTerrain\"/>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t<Element class=\"AssetObjects..StringValue\">\n" +
				"\t\t\t\t\t\t\t<m_Value text=\"ONLY_FIRST_60\"/>\n" +
				"\t\t\t\t\t\t\t<m_ParamName text=\"RotationType\"/>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t</m_Values>\n" +
				"\t\t\t\t</m_Fields>\n" +
				"\t\t\t\t<m_ChildCollections>\n" +
				"\t\t\t\t\t<Element>\n" +
				"\t\t\t\t\t\t<m_CollectionName text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t<m_ReplaceMergedCollectionElements>false</m_ReplaceMergedCollectionElements>\n" +
				"\t\t\t\t\t\t<Element>\n" +
				"\t\t\t\t\t\t\t<m_Fields>\n" +
				"\t\t\t\t\t\t\t\t<m_Values>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ARTERA_ANCIENT\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"ArtEra\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Eras.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Era\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"DEFAULT\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Cultures.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ANY\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"AppealTags\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Appeal.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Appeal\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..BLPEntryValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_EntryName text=\"IMP_Pairidaeza_Ancient\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPClass text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPPath text=\"IMP_Pairidaeza.xlp\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_BLPPackage text=\"IMP_Pairidaeza\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_LibraryName text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Asset\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..StringValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_Value text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"SelectionRule\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..FloatValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_fValue>0.000000</m_fValue>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Priority\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t</m_Values>\n" +
				"\t\t\t\t\t\t\t</m_Fields>\n" +
				"\t\t\t\t\t\t\t<m_ChildCollections/>\n" +
				"\t\t\t\t\t\t\t<m_Name text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t\t<m_AppendMergedParameterCollections>false</m_AppendMergedParameterCollections>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t<Element>\n" +
				"\t\t\t\t\t\t\t<m_Fields>\n" +
				"\t\t\t\t\t\t\t\t<m_Values>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ARTERA_INDUSTRIAL\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"ArtEra\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Eras.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Era\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"DEFAULT\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Cultures.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ANY\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"AppealTags\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Appeal.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Appeal\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..BLPEntryValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_EntryName text=\"IMP_Pairidaeza_Industrial\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPClass text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPPath text=\"IMP_Pairidaeza.xlp\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_BLPPackage text=\"IMP_Pairidaeza\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_LibraryName text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Asset\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..StringValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_Value text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"SelectionRule\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..FloatValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_fValue>0.000000</m_fValue>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Priority\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t</m_Values>\n" +
				"\t\t\t\t\t\t\t</m_Fields>\n" +
				"\t\t\t\t\t\t\t<m_ChildCollections/>\n" +
				"\t\t\t\t\t\t\t<m_Name text=\"Eras001\"/>\n" +
				"\t\t\t\t\t\t\t<m_AppendMergedParameterCollections>false</m_AppendMergedParameterCollections>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t<Element>\n" +
				"\t\t\t\t\t\t\t<m_Fields>\n" +
				"\t\t\t\t\t\t\t\t<m_Values>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ARTERA_MODERN\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"ArtEra\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Eras.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Era\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"DEFAULT\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Cultures.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ANY\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"AppealTags\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Appeal.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Appeal\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..BLPEntryValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_EntryName text=\"IMP_Pairidaeza_Modern\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPClass text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPPath text=\"IMP_Pairidaeza.xlp\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_BLPPackage text=\"IMP_Pairidaeza\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_LibraryName text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Asset\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..StringValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_Value text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"SelectionRule\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..FloatValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_fValue>0.000000</m_fValue>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Priority\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t</m_Values>\n" +
				"\t\t\t\t\t\t\t</m_Fields>\n" +
				"\t\t\t\t\t\t\t<m_ChildCollections/>\n" +
				"\t\t\t\t\t\t\t<m_Name text=\"Eras002\"/>\n" +
				"\t\t\t\t\t\t\t<m_AppendMergedParameterCollections>false</m_AppendMergedParameterCollections>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t<Element>\n" +
				"\t\t\t\t\t\t\t<m_Fields>\n" +
				"\t\t\t\t\t\t\t\t<m_Values>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ARTERA_CLASSICAL\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"ArtEra\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Eras.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"Eras\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Era\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"DEFAULT\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Cultures.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Culture\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..ArtDefReferenceValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ElementName text=\"ANY\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_RootCollectionName text=\"AppealTags\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ArtDefPath text=\"Appeal.artdef\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_CollectionIsLocked>true</m_CollectionIsLocked>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_TemplateName text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Tag_Appeal\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..BLPEntryValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_EntryName text=\"IMP_Pairidaeza\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPClass text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_XLPPath text=\"IMP_Pairidaeza.xlp\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_BLPPackage text=\"IMP_Pairidaeza\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_LibraryName text=\"TileBase\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Asset\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..StringValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_Value text=\"\"/>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"SelectionRule\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t\t<Element class=\"AssetObjects..FloatValue\">\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_fValue>0.000000</m_fValue>\n" +
				"\t\t\t\t\t\t\t\t\t\t<m_ParamName text=\"Priority\"/>\n" +
				"\t\t\t\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t\t\t\t</m_Values>\n" +
				"\t\t\t\t\t\t\t</m_Fields>\n" +
				"\t\t\t\t\t\t\t<m_ChildCollections/>\n" +
				"\t\t\t\t\t\t\t<m_Name text=\"Eras003\"/>\n" +
				"\t\t\t\t\t\t\t<m_AppendMergedParameterCollections>false</m_AppendMergedParameterCollections>\n" +
				"\t\t\t\t\t\t</Element>\n" +
				"\t\t\t\t\t</Element>\n" +
				"\t\t\t\t</m_ChildCollections>\n" +
				"\t\t\t\t<m_Name text=\"LM_PAIRIDAEZA\"/>\n" +
				"\t\t\t\t<m_AppendMergedParameterCollections>false</m_AppendMergedParameterCollections>\n" +
				"\t\t\t</Element>\n" +
				"\t\t</Element>\n" +
				"\t</m_RootCollections>\n" +
				"</AssetObjects..ArtDefSet>\n" +
				"\n";
	}
}
