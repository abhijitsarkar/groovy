package name.abhijitsarkar.moviemanager.domain

class CastAndCrew {
	def name

	CastAndCrew(name) {
		this.name = name
	}

	@Override
	String toString() {
		"${this.class.simpleName}[name:${name}]"
	}

	@Override
	int hashCode() {
		def result = 17
		def c = 0
		def magicNum = 37

		c = name ? name.hashCode() : 0
		result = magicNum * result + c
	}

	@Override
	boolean equals(Object obj) {
		if (this.class != obj?.class) {
			return false
		}

		this.name == obj?.name
	}
}