package name.abhijitsarkar.moviemanager.domain

class CastAndCrew {
	def name

	CastAndCrew(name) {
		this.name = name
	}

	@Override
	int hashCode() {
		int prime = 31
		int result = 1

		prime * result + ((name == null) ? 0 : name.hashCode)
	}

	@Override
	boolean equals(Object obj) {
		if (this.class != obj?.class) {
			return false
		}

		this.name == obj?.name
	}

	String toString() {
		this.name
	}
}