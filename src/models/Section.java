package models;

public class Section {

	private Integer sectionDirection;
	private Integer numbOfSection;
	private RailRoadCrossing crossing;
	private Integer numOfCompositionsOnSection;
	private Integer compositionFirstDirection;
	private Integer compositionSecondDirection;

	public Section() {
		super();
	}

	public Section(Integer numbOfSection, RailRoadCrossing crossing) {
		super();
		this.numbOfSection = numbOfSection;
		this.crossing = crossing;
	}

	public Integer getSectionDirection() {
		return sectionDirection;
	}

	public void setSectionDirection(Integer sectionDirection) {
		this.sectionDirection = sectionDirection;
	}

	public Integer getNumbOfSection() {
		return numbOfSection;
	}

	public void setNumbOfSection(Integer numbOfSection) {
		this.numbOfSection = numbOfSection;
	}

	public RailRoadCrossing getCrossing() {
		return crossing;
	}

	public void setCrossing(RailRoadCrossing crossing) {
		this.crossing = crossing;
	}

	public Integer getNumOfCompositionsOnSection() {
		return numOfCompositionsOnSection;
	}

	public void setNumOfCompositionsOnSection(Integer numOfCompositionsOnSection) {
		this.numOfCompositionsOnSection = numOfCompositionsOnSection;
	}

	public Integer getCompositionFirstDirection() {
		return compositionFirstDirection;
	}

	public void setCompositionFirstDirection(Integer compositionFirstDirection) {
		this.compositionFirstDirection = compositionFirstDirection;
	}

	public Integer getCompositionSecondDirection() {
		return compositionSecondDirection;
	}

	public void setCompositionSecondDirection(Integer compositionSecondDirection) {
		this.compositionSecondDirection = compositionSecondDirection;
	}
}
