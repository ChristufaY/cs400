public class MyList<ElementType> implements ListADT<ElementType> {
	private Object[] _listArray = null;
	private int _size;
	public MyList() {
		_listArray = new Object[100];
		_size = 0;
	}
	@Override
	public void add(ElementType element) {
		if (_listArray.length <= _size) {
			// resize                                                                                 
			Object[] newArray = new Object[_size * 2];
			for (int i = 0; i < _size; i++) {
				newArray[i] = _listArray[i];
			}
			_listArray = newArray;
		}
		_listArray[_size] = element;
		_size++;
	}
	@Override
	@SuppressWarnings("unchecked")
	public ElementType get(int index) {
		if (index >= 0 && index < this._size) {
			return (ElementType)_listArray[index];
		} else {
			throw new IndexOutOfBoundsException("index not in range (index < 0 || index > size())");
		}
	}
	@Override
	public int size() {
		return _size;
	}
	@Override
	public ElementType remove(int index) {
		if (index >= 0 && index < this._size) {
			ElementType deletedElement = this.get(index);
			for (int i = index; i < _size; i++) {
				_listArray[i] = _listArray[i + 1];
				_listArray[i + 1] = null;
			}
			_size--;
			return deletedElement;
		} else {
			throw new IndexOutOfBoundsException("index not in range (index < 0 || index > size())");
		}
	}
	@Override
	public void clear() {
		_listArray = new Object[100];
		_size = 0;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		for (int i = 0; i < this.size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(this.get(i).toString());
		}
		sb.append(" ]");
		return sb.toString();
	}
}