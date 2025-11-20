let organizations = [];

async function fetchOrganizations() {
	try {
		const response = await fetch("/organizations");
		if (!response.ok) throw new Error("Failed to fetch organizations");
		organizations = await response.json();
		renderOrganizations();
		updateStats();
	} catch (error) {
		showToast("Error loading organizations", "error");
	}
}

function updateStats() {
	document.getElementById('total-orgs').textContent = organizations.length;
	document.getElementById('active-orgs').textContent = organizations.length;
	document.getElementById('total-members').textContent = organizations.length;
}

function renderOrganizations() {
	const container = document.getElementById('organizations-list');
	const emptyState = document.getElementById('empty-state');
	container.innerHTML = '';

	if (organizations.length === 0) {
		emptyState.style.display = 'block';
		return;
	}

	emptyState.style.display = 'none';
	organizations.forEach(org => {
		const div = document.createElement('div');
		div.className = 'border p-4 rounded-md hover:shadow-md transition-shadow';
		div.innerHTML = `
			<h4 class="text-lg font-medium">${org.name}</h4>
			<p class="text-sm">${org.email}</p>
			<p class="text-sm">Owner: ${org.ownerName}</p>
		`;
		container.appendChild(div);
	});
}

function showToast(message, type = 'success') {
	const toast = document.createElement('div');
	toast.className = `toast ${type}`;
	toast.textContent = message;
	document.body.appendChild(toast);

	setTimeout(() => toast.classList.add('show'), 100);
	setTimeout(() => {
		toast.classList.remove('show');
		setTimeout(() => document.body.removeChild(toast), 300);
	}, 3000);
}

document.addEventListener("DOMContentLoaded", function () {
	const addBtn = document.getElementById("add-org-btn");
	const addForm = document.getElementById("add-form");
	const cancelBtn = document.getElementById("cancel-btn");
	const form = document.getElementById("organization-form");
	const submitBtn = document.getElementById("submit-btn");

	addBtn.addEventListener("click", () => {
		addForm.classList.remove("hidden");
		addBtn.disabled = true;
	});

	cancelBtn.addEventListener("click", () => {
		addForm.classList.add("hidden");
		addBtn.disabled = false;
		form.reset();
	});

	form.addEventListener("submit", async function (e) {
		e.preventDefault();
		const formData = new FormData(form);

		const orgData = {
			name: formData.get("name"),
			email: formData.get("email"),
			ownerName: formData.get("ownerName")
		};

		submitBtn.disabled = true;
		submitBtn.textContent = "Adding...";

		try {
			const response = await fetch("/organization", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(orgData)
			});

			if (response.ok) {
				showToast("Organization added successfully");
				form.reset();
				addForm.classList.add("hidden");
				addBtn.disabled = false;
				fetchOrganizations();
			} else {
				showToast("Failed to add organization", "error");
			}
		} catch (error) {
			showToast("Server error", "error");
		}

		submitBtn.disabled = false;
		submitBtn.textContent = "Add Organization";
	});

	fetchOrganizations();
});
