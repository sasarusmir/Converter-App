package com.originalit.converter;

public class Unit {

	int _id;
	String _category;
	String _unit;
	String _name;
	double _value;
	
	
	public Unit() {
	}


	/**
	 * @param _id
	 * @param _category
	 * @param _unit
	 * @param _name
	 * @param _value
	 */
	public Unit(int _id, String _category, String _unit, String _name, double _value) {
		super();
		this._id = _id;
		this._category = _category;
		this._unit = _unit;
		this._name = _name;
		this._value = _value;
	}


	/**
	 * @param _category
	 * @param _unit
	 * @param _name
	 * @param _value
	 */
	public Unit(String _category, String _unit, String _name, double _value) {
		super();
		this._category = _category;
		this._unit = _unit;
		this._name = _name;
		this._value = _value;
	}


	public int get_id() {
		return _id;
	}


	public void set_id(int _id) {
		this._id = _id;
	}


	public String get_category() {
		return _category;
	}


	public void set_category(String _category) {
		this._category = _category;
	}


	public String get_unit() {
		return _unit;
	}


	public void set_unit(String _unit) {
		this._unit = _unit;
	}
	
	public String get_name() {
		return _name;
	}


	public void set_name(String _name) {
		this._name = _name;
	}


	public double get_value() {
		return  _value;
	}


	public void set_value(double _value) {
		this._value = _value;
	}


	
	
	
}
