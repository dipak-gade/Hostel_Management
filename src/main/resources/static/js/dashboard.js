/* Combined + cleaned JS
   - keeps original IDs and behavior
   - initializes SDKs if present
   - fetches hostels from backend at /hostels
   - uses dataSdk when available for create/update/delete
*/

let allData = [];
let currentRecordCount = 0;
let currentLevel = 'hostels';
let currentParentId = null;
let navigationStack = [];

const defaultConfig = {
	organization_name: "TechCorp Organization",
	welcome_message: "Welcome to Organization Dashboard",
	add_button_text: "Add New Hostel",
	primary_color: "#3b82f6",
	surface_color: "#ffffff",
	text_color: "#111827",
	success_color: "#10b981",
	background_color: "#f9fafb"
};

// Data SDK Handler
const dataHandler = {
	onDataChanged(data) {
		allData = data || [];
		currentRecordCount = allData.length;
		updateStats();
		renderCurrentLevel();
	}
};

// Element SDK Implementation
async function onConfigChange(config) {
	const orgName = document.getElementById('organization-name');
	const welcomeMessage = document.getElementById('welcome-message');
	const addButtonText = document.getElementById('add-button-text');

	if (orgName) {
		orgName.textContent = config.organization_name || defaultConfig.organization_name;
		orgName.style.color = config.text_color || defaultConfig.text_color;
	}
	if (welcomeMessage) {
		welcomeMessage.textContent = config.welcome_message || defaultConfig.welcome_message;
	}
	if (addButtonText) {
		addButtonText.textContent = config.add_button_text || defaultConfig.add_button_text;
	}

	// Apply colors
	document.body.style.backgroundColor = config.background_color || defaultConfig.background_color;

	const surfaces = document.querySelectorAll('.bg-white');
	surfaces.forEach(surface => {
		surface.style.backgroundColor = config.surface_color || defaultConfig.surface_color;
	});

	const primaryButtons = document.querySelectorAll('.bg-blue-600');
	primaryButtons.forEach(btn => {
		btn.style.backgroundColor = config.primary_color || defaultConfig.primary_color;
	});
}

function mapToCapabilities(config) {
	return {
		recolorables: [
			{
				get: () => config.background_color || defaultConfig.background_color,
				set: (value) => {
					config.background_color = value;
					if (window.elementSdk) window.elementSdk.setConfig({ background_color: value });
				}
			},
			{
				get: () => config.surface_color || defaultConfig.surface_color,
				set: (value) => {
					config.surface_color = value;
					if (window.elementSdk) window.elementSdk.setConfig({ surface_color: value });
				}
			},
			{
				get: () => config.text_color || defaultConfig.text_color,
				set: (value) => {
					config.text_color = value;
					if (window.elementSdk) window.elementSdk.setConfig({ text_color: value });
				}
			},
			{
				get: () => config.primary_color || defaultConfig.primary_color,
				set: (value) => {
					config.primary_color = value;
					if (window.elementSdk) window.elementSdk.setConfig({ primary_color: value });
				}
			},
			{
				get: () => config.success_color || defaultConfig.success_color,
				set: (value) => {
					config.success_color = value;
					if (window.elementSdk) window.elementSdk.setConfig({ success_color: value });
				}
			}
		],
		borderables: [],
		fontEditable: undefined,
		fontSizeable: undefined
	};
}

function mapToEditPanelValues(config) {
	return new Map([
		["organization_name", config.organization_name || defaultConfig.organization_name],
		["welcome_message", config.welcome_message || defaultConfig.welcome_message],
		["add_button_text", config.add_button_text || defaultConfig.add_button_text]
	]);
}

// Init SDKs (if available) and data handler
async function initSdks() {
	try {
		if (window.elementSdk) {
			await window.elementSdk.init({
				defaultConfig,
				onConfigChange,
				mapToCapabilities,
				mapToEditPanelValues
			});
		}

		if (window.dataSdk) {
			const result = await window.dataSdk.init(dataHandler);
			if (!result.isOk) {
				showToast('Failed to initialize data storage', 'error');
			}
		}
	} catch (error) {
		showToast('Failed to initialize application', 'error');
		console.error(error);
	}
}

/* Utility Functions */
function showToast(message, type = 'success') {
	const toast = document.createElement('div');
	toast.className = `toast ${type}`;
	toast.textContent = message;
	document.body.appendChild(toast);

	setTimeout(() => toast.classList.add('show'), 100);
	setTimeout(() => {
		toast.classList.remove('show');
		setTimeout(() => {
			if (toast.parentNode) toast.parentNode.removeChild(toast);
		}, 300);
	}, 3000);
}

function generateId() {
	return currentLevel.slice(0, -1) + '_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
}

function getFilteredData() {
	return allData.filter(item => {
		if (currentLevel === 'hostels') {
			return item.entityType === 'hostel';
		} else if (currentLevel === 'buildings') {
			return item.entityType === 'building' && item.parentId === currentParentId;
		} else if (currentLevel === 'floors') {
			return item.entityType === 'floor' && item.parentId === currentParentId;
		} else if (currentLevel === 'rooms') {
			return item.entityType === 'room' && item.parentId === currentParentId;
		} else if (currentLevel === 'beds') {
			return item.entityType === 'bed' && item.parentId === currentParentId;
		}
		return [];
	});
}

function updateStats() {
	const hostels = allData.filter(item => item.entityType === 'hostel');
	const buildings = allData.filter(item => item.entityType === 'building');
	const floors = allData.filter(item => item.entityType === 'floor');
	const rooms = allData.filter(item => item.entityType === 'room');
	const beds = allData.filter(item => item.entityType === 'bed');
	const availableBeds = beds.filter(bed => bed.status === 'available');
	const occupiedBeds = beds.filter(bed => bed.status === 'occupied');
	const maintenanceBeds = beds.filter(bed => bed.status === 'maintenance');

	// Update sidebar counts
	document.getElementById('hostels-count').textContent = hostels.length;
	document.getElementById('buildings-count').textContent = buildings.length;
	document.getElementById('floors-count').textContent = floors.length;
	document.getElementById('rooms-count').textContent = rooms.length;
	document.getElementById('beds-count').textContent = beds.length;

	// Update sidebar quick stats
	document.getElementById('sidebar-available-beds').textContent = availableBeds.length;
	document.getElementById('sidebar-occupied-beds').textContent = occupiedBeds.length;
	document.getElementById('sidebar-maintenance-beds').textContent = maintenanceBeds.length;

	if (currentLevel === 'hostels') {
		document.getElementById('stat1-label').textContent = 'Total Hostels';
		document.getElementById('stat1-value').textContent = hostels.length;
		document.getElementById('stat2-label').textContent = 'Active Hostels';
		document.getElementById('stat2-value').textContent = hostels.filter(h => h.status === 'active').length;
		document.getElementById('stat3-label').textContent = 'Total Buildings';
		document.getElementById('stat3-value').textContent = buildings.length;
		document.getElementById('stat4-label').textContent = 'Available Beds';
		document.getElementById('stat4-value').textContent = availableBeds.length;

		// Update active navigation item
		updateActiveNavItem();
	} else if (currentLevel === 'buildings') {
		const currentBuildings = getFilteredData();
		document.getElementById('stat1-label').textContent = 'Total Buildings';
		document.getElementById('stat1-value').textContent = currentBuildings.length;
		document.getElementById('stat2-label').textContent = 'Active Buildings';
		document.getElementById('stat2-value').textContent = currentBuildings.filter(b => b.status === 'active').length;
		document.getElementById('stat3-label').textContent = 'Total Floors';
		document.getElementById('stat3-value').textContent = allData.filter(item => item.entityType === 'floor' && currentBuildings.some(b => item.parentId === b.id)).length;
		document.getElementById('stat4-label').textContent = 'Total Rooms';
		document.getElementById('stat4-value').textContent = rooms.length;
	} else if (currentLevel === 'floors') {
		const currentFloors = getFilteredData();
		document.getElementById('stat1-label').textContent = 'Total Floors';
		document.getElementById('stat1-value').textContent = currentFloors.length;
		document.getElementById('stat2-label').textContent = 'Active Floors';
		document.getElementById('stat2-value').textContent = currentFloors.filter(f => f.status === 'active').length;
		document.getElementById('stat3-label').textContent = 'Total Rooms';
		document.getElementById('stat3-value').textContent = allData.filter(item => item.entityType === 'room' && currentFloors.some(f => item.parentId === f.id)).length;
		document.getElementById('stat4-label').textContent = 'Total Beds';
		document.getElementById('stat4-value').textContent = beds.length;
	} else if (currentLevel === 'rooms') {
		const currentRooms = getFilteredData();
		document.getElementById('stat1-label').textContent = 'Total Rooms';
		document.getElementById('stat1-value').textContent = currentRooms.length;
		document.getElementById('stat2-label').textContent = 'Active Rooms';
		document.getElementById('stat2-value').textContent = currentRooms.filter(r => r.status === 'active').length;
		document.getElementById('stat3-label').textContent = 'Total Beds';
		document.getElementById('stat3-value').textContent = allData.filter(item => item.entityType === 'bed' && currentRooms.some(r => item.parentId === r.id)).length;
		document.getElementById('stat4-label').textContent = 'Available Beds';
		document.getElementById('stat4-value').textContent = allData.filter(item => item.entityType === 'bed' && item.bedStatus === 'available' && currentRooms.some(r => item.parentId === r.id)).length;
	} else if (currentLevel === 'beds') {
		const currentBeds = getFilteredData();
		document.getElementById('stat1-label').textContent = 'Total Beds';
		document.getElementById('stat1-value').textContent = currentBeds.length;
		document.getElementById('stat2-label').textContent = 'Available Beds';
		document.getElementById('stat2-value').textContent = currentBeds.filter(b => b.bedStatus === 'available').length;
		document.getElementById('stat3-label').textContent = 'Occupied Beds';
		document.getElementById('stat3-value').textContent = currentBeds.filter(b => b.bedStatus === 'occupied').length;
		document.getElementById('stat4-label').textContent = 'Maintenance';
		document.getElementById('stat4-value').textContent = currentBeds.filter(b => b.bedStatus === 'maintenance').length;
	}
}

function updateActiveNavItem() {
	document.querySelectorAll('.nav-item').forEach(item => item.classList.remove('active'));
	const activeItem = document.querySelector(`[data-level="${currentLevel}"]`);
	if (activeItem) activeItem.classList.add('active');
}

function navigateToLevel(level) {
	currentLevel = level;
	currentParentId = null;
	navigationStack = [];

	if (level !== 'beds') {
		document.getElementById('content-grid').className = 'grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6';
	}

	renderCurrentLevel();
}

function updateUI() {
	const levelTitles = {
		'hostels': 'Hostels Management',
		'buildings': 'Buildings Management',
		'floors': 'Floors Management',
		'rooms': 'Rooms Management',
		'beds': 'Beds Management'
	};

	const addButtonTexts = {
		'hostels': 'Add New Hostel',
		'buildings': 'Add New Building',
		'floors': 'Add New Floor',
		'rooms': 'Add New Room',
		'beds': 'Add New Bed'
	};

	const emptyStateTexts = {
		'hostels': 'Get started by adding your first hostel.',
		'buildings': 'Add buildings to this hostel.',
		'floors': 'Add floors to this building.',
		'rooms': 'Add rooms to this floor.',
		'beds': 'Add beds to this room.'
	};

	document.getElementById('section-title').textContent = levelTitles[currentLevel];
	document.getElementById('add-button-text').textContent = addButtonTexts[currentLevel];
	document.getElementById('empty-state-text').textContent = emptyStateTexts[currentLevel];

	// Show/hide back button
	const backBtn = document.getElementById('back-btn');
	if (currentLevel === 'hostels') {
		backBtn.classList.add('hidden');
	} else {
		backBtn.classList.remove('hidden');
	}
}

function renderCurrentLevel() {
	updateUI();
	const data = getFilteredData();
	const container = document.getElementById('content-grid');
	const emptyState = document.getElementById('empty-state');

	// Clear existing content except empty state
	const existingItems = container.querySelectorAll('[data-item-id]');
	existingItems.forEach(item => item.remove());

	if (!data || data.length === 0) {
		emptyState.style.display = 'block';
		return;
	}
	emptyState.style.display = 'none';

	if (currentLevel === 'beds') {
		renderBedsGrid(data);
	} else {
		data.forEach(item => {
			container.appendChild(createItemElement(item));
		});
	}
}

function renderBedsGrid(beds) {
	const container = document.getElementById('content-grid');
	container.className = 'bed-grid';

	beds.forEach(bed => {
		const bedElement = document.createElement('div');
		bedElement.className = `bed-item ${bed.status}`;
		bedElement.dataset.itemId = bed.id;
		bedElement.innerHTML = `
      <div class="font-medium">Bed ${bed.bedNo}</div>
      <div class="text-xs mt-1">${bed.status}</div>
      <div class="text-xs mt-1 font-semibold text-green-600">₹${bed.price || 0}/month</div>
    `;
		bedElement.onclick = () => toggleBedStatus(bed.id);
		container.appendChild(bedElement);
	});
}

function createItemElement(item) {
	const div = document.createElement('div');
	div.className = `${currentLevel.slice(0, -1)}-card bg-white rounded-lg shadow-md overflow-hidden`;
	div.dataset.itemId = item.id;

	if (currentLevel === 'hostels') {
		div.innerHTML = createHostelCard(item);
	} else if (currentLevel === 'buildings') {
		div.innerHTML = createBuildingCard(item);
	} else if (currentLevel === 'floors') {
		div.innerHTML = createFloorCard(item);
	} else if (currentLevel === 'rooms') {
		div.innerHTML = createRoomCard(item);
	}

	return div;
}

function createHostelCard(hostel) {
	const statusColor = hostel.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800';
	const createdDate = hostel.createdAt ? new Date(hostel.createdAt).toLocaleDateString() : '';
	const buildingCount = allData.filter(item => item.entityType === 'building' && item.parentId === hostel.id).length;

	const imageContent = hostel.image ?
		`<img src="${hostel.image}" alt="${hostel.name}" class="hostel-image" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
     <div class="hostel-image-placeholder" style="display: none;">🏢</div>` :
		`<div class="hostel-image-placeholder">🏢</div>`;

	return `
    <div class="relative">
      ${imageContent}
      <div class="absolute top-2 right-2">
        <span class="px-2 py-1 text-xs font-medium rounded-full ${statusColor}">
          ${hostel.status}
        </span>
      </div>
    </div>
    <div class="p-4">
      <div class="flex justify-between items-start mb-2">
        <h4 class="text-lg font-semibold text-gray-900 cursor-pointer hover:text-blue-600" onclick="navigateToBuildings('${hostel.id}')">${hostel.name}</h4>
        <span class="text-sm font-medium text-blue-600">${hostel.type}</span>
      </div>
      <p class="text-sm text-gray-600 mb-3 line-clamp-2">${hostel.address || ''}</p>
      <div class="flex items-center justify-between mb-4">
        <div class="flex items-center text-sm text-gray-500">
          <svg class="h-4 w-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-4m-2 0H9m-2 0H5m2 0v-4a2 2 0 012-2h2a2 2 0 012 2v4"></path>
          </svg>
          ${buildingCount} Buildings
        </div>
        <span class="text-xs text-gray-400">${createdDate}</span>
      </div>
      <div class="flex space-x-2">
        <button onclick="navigateToBuildings('${hostel.id}')" class="flex-1 text-sm px-3 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
          Add Buildings
        </button>
        <button onclick="toggleStatus('${hostel.id}')" class="text-sm px-3 py-2 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          ${hostel.status === 'active' ? 'Deactivate' : 'Activate'}
        </button>
        <button onclick="confirmDelete('${hostel.id}')" class="text-sm px-3 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
          Delete
        </button>
      </div>
    </div>
  `;
}

function createBuildingCard(building) {
	const statusColor = building.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800';
	const createdDate = building.createdAt ? new Date(building.createdAt).toLocaleDateString() : '';
	const floorCount = allData.filter(item => item.entityType === 'floor' && item.parentId === building.id).length;

	return `
    <div class="p-4">
      <div class="flex justify-between items-start mb-2">
        <h4 class="text-lg font-semibold text-gray-900 cursor-pointer hover:text-blue-600" onclick="navigateToFloors('${building.id}')">${building.name}</h4>
        <span class="px-2 py-1 text-xs font-medium rounded-full ${statusColor}">
          ${building.status}
        </span>
      </div>
      <div class="space-y-2 mb-3">
        <div class="flex items-center text-sm text-gray-600">
          <svg class="h-4 w-4 mr-2 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-4m-2 0H9m-2 0H5m2 0v-4a2 2 0 012-2h2a2 2 0 012 2v4"></path>
          </svg>
          <span class="font-medium">Floors:</span> ${building.floors || 'N/A'}
        </div>
        <div class="flex items-center text-sm text-gray-600">
          <svg class="h-4 w-4 mr-2 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
          </svg>
          <span class="font-medium">Warden:</span> ${building.warden || 'Not assigned'}
        </div>
      </div>
      <div class="flex items-center justify-between mb-4">
        <div class="flex items-center text-sm text-gray-500">
          <svg class="h-4 w-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path>
          </svg>
          ${floorCount} Floors Created
        </div>
        <span class="text-xs text-gray-400">${createdDate}</span>
      </div>
      <div class="flex space-x-2">
        <button onclick="navigateToFloors('${building.id}')" class="flex-1 text-sm px-3 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
          Add Floors
        </button>
        <button onclick="toggleStatus('${building.id}')" class="text-sm px-3 py-2 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          ${building.status === 'active' ? 'Deactivate' : 'Activate'}
        </button>
        <button onclick="confirmDelete('${building.id}')" class="text-sm px-3 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
          Delete
        </button>
      </div>
    </div>
  `;
}

function createFloorCard(floor) {
	const statusColor = floor.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800';
	const createdDate = floor.createdAt ? new Date(floor.createdAt).toLocaleDateString() : '';
	const roomCount = allData.filter(item => item.entityType === 'room' && item.parentId === floor.id).length;

	return `
    <div class="p-4">
      <div class="flex justify-between items-start mb-2">
        <h4 class="text-lg font-semibold text-gray-900 cursor-pointer hover:text-blue-600" onclick="navigateToRooms('${floor.id}')">Floor ${floor.floorNo}</h4>
        <span class="px-2 py-1 text-xs font-medium rounded-full ${statusColor}">
          ${floor.status}
        </span>
      </div>
      <div class="space-y-2 mb-3">
        <div class="flex items-center text-sm text-gray-600">
          <svg class="h-4 w-4 mr-2 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2 2v0"></path>
          </svg>
          <span class="font-medium">Total Rooms:</span> ${floor.noOfRooms || 'N/A'}
        </div>
      </div>
      <div class="flex items-center justify-between mb-4">
        <div class="flex items-center text-sm text-gray-500">
          <svg class="h-4 w-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2 2v0"></path>
          </svg>
          ${roomCount} Rooms Created
        </div>
        <span class="text-xs text-gray-400">${createdDate}</span>
      </div>
      <div class="flex space-x-2">
        <button onclick="navigateToRooms('${floor.id}')" class="flex-1 text-sm px-3 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
          Add Rooms
        </button>
        <button onclick="toggleStatus('${floor.id}')" class="text-sm px-3 py-2 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          ${floor.status === 'active' ? 'Deactivate' : 'Activate'}
        </button>
        <button onclick="confirmDelete('${floor.id}')" class="text-sm px-3 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
          Delete
        </button>
      </div>
    </div>
  `;
}

function createRoomCard(room) {
	const statusColor = room.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800';
	const createdDate = room.createdAt ? new Date(room.createdAt).toLocaleDateString() : '';
	const bedCount = allData.filter(item => item.entityType === 'bed' && item.parentId === room.id).length;
	const availableBeds = allData.filter(item => item.entityType === 'bed' && item.parentId === room.id && item.status === 'available').length;

	return `
    <div class="p-4">
      <div class="flex justify-between items-start mb-2">
        <h4 class="text-lg font-semibold text-gray-900 cursor-pointer hover:text-blue-600" onclick="navigateToBeds('${room.id}')">Room ${room.roomNo}</h4>
        <span class="px-2 py-1 text-xs font-medium rounded-full ${statusColor}">
          ${room.status}
        </span>
      </div>
      <div class="space-y-2 mb-3">
        <div class="flex items-center justify-between">
          <span class="text-sm font-medium text-purple-600">${room.type}</span>
          <span class="text-sm text-gray-500">${availableBeds}/${bedCount} available</span>
        </div>
        <div class="flex items-center text-sm text-gray-600">
          <svg class="h-4 w-4 mr-2 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
          </svg>
          <span class="font-medium">Sharing:</span> ${room.sharing || 'N/A'} beds
        </div>
      </div>
      <div class="flex items-center justify-between mb-4">
        <div class="flex items-center text-sm text-gray-500">
          <svg class="h-4 w-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"></path>
          </svg>
          ${bedCount} Beds Created
        </div>
        <span class="text-xs text-gray-400">${createdDate}</span>
      </div>
      <div class="flex space-x-2">
        <button onclick="navigateToBeds('${room.id}')" class="flex-1 text-sm px-3 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
          Add Beds
        </button>
        <button onclick="toggleStatus('${room.id}')" class="text-sm px-3 py-2 border border-gray-300 rounded-md hover:bg-gray-50 transition-colors">
          ${room.status === 'active' ? 'Deactivate' : 'Activate'}
        </button>
        <button onclick="confirmDelete('${room.id}')" class="text-sm px-3 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors">
          Delete
        </button>
      </div>
    </div>
  `;
}

/* Navigation functions */
function navigateToBuildings(hostelId) {
    console.log("navigateToBuildings called with:", hostelId);
    if (!hostelId) return;
    loadBuildings();
}



function navigateToFloors(buildingId) {
	if (!buildingId) return;

	const building = allData.find(item => item.id === buildingId);
	const buildingName = building?.name || "Building";

	// Push previous state to stack
	navigationStack.push({
		level: currentLevel,
		parentId: currentParentId,
		name: buildingName
	});

	// Update current level and parent
	currentLevel = "floors";
	currentParentId = buildingId;

	// Update breadcrumb
	updateBreadcrumb(buildingName);

	// Render floors page
	renderCurrentLevel();
}


function navigateToRooms(floorId) {
	if (floorId) {
		const floor = allData.find(item => item.id === floorId);
		navigationStack.push({ level: currentLevel, parentId: currentParentId, name: `Floor ${floor && floor.floorNo ? floor.floorNo : ''}` });
		currentLevel = 'rooms';
		currentParentId = floorId;
		updateBreadcrumb(`Floor ${floor && floor.floorNo ? floor.floorNo : ''}`);
	}
	renderCurrentLevel();
}

function navigateToBeds(roomId) {
	if (roomId) {
		const room = allData.find(item => item.id === roomId);
		navigationStack.push({ level: currentLevel, parentId: currentParentId, name: `Room ${room && room.roomNo ? room.roomNo : ''}` });
		currentLevel = 'beds';
		currentParentId = roomId;
		updateBreadcrumb(`Room ${room && room.roomNo ? room.roomNo : ''}`);
		document.getElementById('content-grid').className = 'bed-grid';
	}
	renderCurrentLevel();
}

function navigateBack() {
	if (navigationStack.length > 0) {
		const previous = navigationStack.pop();
		currentLevel = previous.level;
		currentParentId = previous.parentId;
		updateBreadcrumbBack();
		if (currentLevel !== 'beds') {
			document.getElementById('content-grid').className = 'grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6';
		}
		renderCurrentLevel();
	}
}

function updateBreadcrumb(name) {
	// simplified: show/hide second breadcrumb text nodes if necessary
	const bld = document.getElementById('breadcrumb-building');
	const flr = document.getElementById('breadcrumb-floor');
	const rm = document.getElementById('breadcrumb-room');

	// reset
	bld.classList.add('hidden');
	flr.classList.add('hidden');
	rm.classList.add('hidden');

	if (currentLevel === 'buildings') {
		bld.textContent = name;
		bld.classList.remove('hidden');
	} else if (currentLevel === 'floors') {
		bld.classList.remove('hidden');
		flr.textContent = name;
		flr.classList.remove('hidden');
	} else if (currentLevel === 'rooms') {
		bld.classList.remove('hidden');
		flr.classList.remove('hidden');
		rm.textContent = name;
		rm.classList.remove('hidden');
	}
}

function updateBreadcrumbBack() {
	// naive: re-render breadcrumb from navigationStack top (simple)
	const last = navigationStack.length > 0 ? navigationStack[navigationStack.length - 1] : null;
	if (!last) {
		document.getElementById('breadcrumb-building').classList.add('hidden');
		document.getElementById('breadcrumb-floor').classList.add('hidden');
		document.getElementById('breadcrumb-room').classList.add('hidden');
	} else {
		updateBreadcrumb(last.name || '');
	}
}

/* Event handlers and CRUD via dataSdk */
async function toggleStatus(itemId) {
	const item = allData.find(i => i.id === itemId);
	if (!item) return;

	const updatedItem = { ...item, status: item.status === 'active' ? 'inactive' : 'active' };

	if (window.dataSdk) {
		const result = await window.dataSdk.update(updatedItem);
		if (result.isOk) {
			showToast(`${item.entityType} ${updatedItem.status === 'active' ? 'activated' : 'deactivated'} successfully`);
		} else {
			showToast(`Failed to update ${item.entityType} status`, 'error');
		}
	} else {
		// fallback local update
		item.status = updatedItem.status;
		updateStats();
		renderCurrentLevel();
		showToast(`(local) ${item.entityType} updated`);
	}
}

async function toggleBedStatus(bedId) {
	const bed = allData.find(b => b.id === bedId);
	if (!bed) return;
	const statusCycle = { 'available': 'occupied', 'occupied': 'maintenance', 'maintenance': 'available' };
	const updatedBed = { ...bed, status: statusCycle[bed.status] || 'available' };

	if (window.dataSdk) {
		const result = await window.dataSdk.update(updatedBed);
		if (result.isOk) {
			showToast(`Bed ${bed.bedNo} status updated to ${updatedBed.status}`);
		} else {
			showToast('Failed to update bed status', 'error');
		}
	} else {
		bed.status = updatedBed.status;
		updateStats();
		renderCurrentLevel();
		showToast(`(local) Bed ${bed.bedNo} updated`);
	}
}

function confirmDelete(itemId) {
	const item = allData.find(i => i.id === itemId);
	if (!item) return;

	const itemElement = document.querySelector(`[data-item-id="${itemId}"]`);
	const deleteButton = itemElement.querySelector('button[onclick*="confirmDelete"]');

	deleteButton.innerHTML = 'Confirm Delete?';
	deleteButton.className = 'text-sm px-3 py-2 bg-red-700 text-white rounded-md hover:bg-red-800 transition-colors';
	deleteButton.onclick = () => deleteItem(itemId);

	setTimeout(() => {
		if (!deleteButton) return;
		deleteButton.innerHTML = 'Delete';
		deleteButton.className = 'text-sm px-3 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors';
		deleteButton.onclick = () => confirmDelete(itemId);
	}, 3000);
}

async function deleteItem(itemId) {
	const item = allData.find(i => i.id === itemId);
	if (!item) return;

	if (window.dataSdk) {
		const result = await window.dataSdk.delete(item);
		if (result.isOk) {
			showToast(`${item.entityType} deleted successfully`);
		} else {
			showToast(`Failed to delete ${item.entityType}`, 'error');
		}
	} else {
		// fallback local delete
		allData = allData.filter(i => i.id !== itemId);
		updateStats();
		renderCurrentLevel();
		showToast('(local) item deleted');
	}
}

/* Forms */
function showAddForm() {
	if (currentRecordCount >= 999) {
		showToast('Maximum limit of 999 items reached. Please delete some items first.', 'error');
		return;
	}

	const formsContainer = document.getElementById('forms-container');
	formsContainer.innerHTML = getFormHTML();
	formsContainer.classList.remove('hidden');
	document.getElementById('add-item-btn').disabled = true;
}

function hideAddForm() {
	const formsContainer = document.getElementById('forms-container');
	formsContainer.innerHTML = '';
	formsContainer.classList.add('hidden');
	document.getElementById('add-item-btn').disabled = false;
}

function getFormButtons() {
	return `
    <div class="flex justify-end space-x-3">
      <button type="button" onclick="hideAddForm()" class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
        Cancel
      </button>
      <button type="submit" class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 disabled:opacity-50 disabled:cursor-not-allowed">
        <span class="submit-text">Add ${currentLevel.slice(0, -1)}</span>
        <div class="loading-spinner ml-2 hidden"></div>
      </button>
    </div>
  `;
}

function getFormHTML() {
	const forms = {
		'hostels': `
      <div class="mb-6 p-4 bg-gray-50 rounded-lg">
        <h4 class="text-md font-medium text-gray-900 mb-4">Add New Hostel</h4>
        <form id="item-form" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="item-name" class="block text-sm font-medium text-gray-700">Hostel Name</label>
              <input type="text" id="item-name" name="name" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-org-id" class="block text-sm font-medium text-gray-700">Organization ID</label>
              <input type="text" id="item-org-id" name="orgId" required placeholder="Enter organization ID" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-type" class="block text-sm font-medium text-gray-700">Hostel Type</label>
              <select id="item-type" name="type" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
                <option value="">Select Type</option>
                <option value="Boys">Boys</option>
                <option value="Girls">Girls</option>
              </select>
            </div>
            <div>
              <label for="item-capacity" class="block text-sm font-medium text-gray-700">Capacity</label>
              <input type="number" id="item-capacity" name="capacity" min="1" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div class="md:col-span-2">
              <label for="item-address" class="block text-sm font-medium text-gray-700">Address</label>
              <textarea id="item-address" name="address" rows="3" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border"></textarea>
            </div>
            <div class="md:col-span-2">
              <label for="item-image" class="block text-sm font-medium text-gray-700">Image URL (Optional)</label>
              <input type="url" id="item-image" name="image" placeholder="https://example.com/image.jpg" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
          </div>
          ${getFormButtons()}
        </form>
      </div>
    `,
		'buildings': `
      <div class="mb-6 p-4 bg-gray-50 rounded-lg">
        <h4 class="text-md font-medium text-gray-900 mb-4">Add New Building</h4>
        <form id="item-form" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
		  <div>
		    <label for="hostel-id" class="block text-sm font-medium text-gray-700">Hostel ID</label>
		    <input type="number" id="hostel-id" name="hostelId" required
		           class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
		  </div>
            <div>
              <label for="item-name" class="block text-sm font-medium text-gray-700">Building Name</label>
              <input type="text" id="item-name" name="name" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-floors" class="block text-sm font-medium text-gray-700">Number of Floors</label>
              <input type="number" id="item-floors" name="floors" min="1" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div class="md:col-span-2">
              <label for="item-warden" class="block text-sm font-medium text-gray-700">Warden Name</label>
              <input type="text" id="item-warden" name="warden" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
          </div>
          ${getFormButtons()}
        </form>
      </div>
    `,
		'floors': `
      <div class="mb-6 p-4 bg-gray-50 rounded-lg">
        <h4 class="text-md font-medium text-gray-900 mb-4">Add New Floor</h4>
        <form id="item-form" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label for="item-floor-no" class="block text-sm font-medium text-gray-700">Floor Number</label>
              <input type="number" id="item-floor-no" name="floorNo" min="0" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-no-of-rooms" class="block text-sm font-medium text-gray-700">Number of Rooms</label>
              <input type="number" id="item-no-of-rooms" name="noOfRooms" min="1" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
          </div>
          ${getFormButtons()}
        </form>
      </div>
    `,
		'rooms': `
      <div class="mb-6 p-4 bg-gray-50 rounded-lg">
        <h4 class="text-md font-medium text-gray-900 mb-4">Add New Room</h4>
        <form id="item-form" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label for="item-room-no" class="block text-sm font-medium text-gray-700">Room Number</label>
              <input type="number" id="item-room-no" name="roomNo" min="1" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-sharing" class="block text-sm font-medium text-gray-700">Sharing (Number of Beds)</label>
              <input type="number" id="item-sharing" name="sharing" min="1" max="20" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-type" class="block text-sm font-medium text-gray-700">Room Type</label>
              <select id="item-type" name="type" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
                <option value="">Select Type</option>
                <option value="AC">AC Room</option>
                <option value="Non-AC">Non-AC Room</option>
                <option value="Deluxe">Deluxe Room</option>
                <option value="Standard">Standard Room</option>
                <option value="Premium">Premium Room</option>
              </select>
            </div>
          </div>
          ${getFormButtons()}
        </form>
      </div>
    `,
		'beds': `
      <div class="mb-6 p-4 bg-gray-50 rounded-lg">
        <h4 class="text-md font-medium text-gray-900 mb-4">Add New Bed</h4>
        <form id="item-form" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label for="item-bed-no" class="block text-sm font-medium text-gray-700">Bed Number</label>
              <input type="number" id="item-bed-no" name="bedNo" min="1" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
            <div>
              <label for="item-status" class="block text-sm font-medium text-gray-700">Bed Status</label>
              <select id="item-status" name="status" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
                <option value="available">Available</option>
                <option value="occupied">Occupied</option>
                <option value="maintenance">Maintenance</option>
              </select>
            </div>
            <div>
              <label for="item-price" class="block text-sm font-medium text-gray-700">Price per Month (₹)</label>
              <input type="number" id="item-price" name="price" min="0" step="100" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm px-3 py-2 border">
            </div>
          </div>
          ${getFormButtons()}
        </form>
      </div>
    `
	};

	return forms[currentLevel] || '';
}

async function handleFormSubmit(e) {
	e.preventDefault();

	if (currentRecordCount >= 999) {
		showToast('Maximum limit of 999 items reached. Please delete some items first.', 'error');
		return;
	}

	const formData = new FormData(e.target);
	const submitBtn = e.target.querySelector('button[type="submit"]');
	const submitText = submitBtn.querySelector('.submit-text');
	const spinner = submitBtn.querySelector('.loading-spinner');

	submitBtn.disabled = true;
	submitText.textContent = 'Adding...';
	spinner.classList.remove('hidden');

	let itemData = {
		id: generateId(),
		entityType: currentLevel.slice(0, -1),
		parentId: currentParentId,
		status: 'active',
		createdAt: new Date().toISOString()
	};

	// level-specific
	if (currentLevel === 'hostels') {
		itemData = {
			...itemData,
			name: formData.get('name'),
			orgId: formData.get('orgId'),
			address: formData.get('address'),
			capacity: parseInt(formData.get('capacity')) || 0,
			type: formData.get('type'),
			image: formData.get('image') || ''
		};
	} else if (currentLevel === 'buildings') {
		itemData = {
			...itemData,
			hostelId: parseInt(formData.get('hostelId')) || 0,
			name: formData.get('name'),
			floors: parseInt(formData.get('floors')) || 0,
			warden: formData.get('warden') || ''
		};
	}
	else if (currentLevel === 'floors') {
		itemData = {
			...itemData,
			name: `Floor ${formData.get('floorNo')}`,
			floorNo: parseInt(formData.get('floorNo')) || 0,
			noOfRooms: parseInt(formData.get('noOfRooms')) || 0
		};
	} else if (currentLevel === 'rooms') {
		itemData = {
			...itemData,
			name: `Room ${formData.get('roomNo')}`,
			roomNo: parseInt(formData.get('roomNo')) || 0,
			sharing: parseInt(formData.get('sharing')) || 0,
			type: formData.get('type')
		};
	} else if (currentLevel === 'beds') {
		itemData = {
			...itemData,
			name: `Bed ${formData.get('bedNo')}`,
			bedNo: parseInt(formData.get('bedNo')) || 0,
			status: formData.get('status'),
			price: parseInt(formData.get('price')) || 0
		};
	}

	// If current form is for hostel -> call backend API (as in original)
	if (currentLevel === "hostels") {
		try {
			const response = await fetch("http://localhost:8080/hostelWithOrgId", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({
					name: formData.get("name"),
					orgId: formData.get("orgId"),
					address: formData.get("address"),
					capacity: parseInt(formData.get("capacity")) || 0,
					type: formData.get("type"),
					image: formData.get("image") || ""
				})
			});

			if (response.ok) {
				showToast("Hostel added successfully");
				hideAddForm();
				// reload hostels from backend
				await loadHostels();
			} else {
				showToast("Failed to add hostel", "error");
			}
		} catch (err) {
			console.error(err);
			showToast("Error connecting to backend", "error");
		}

		submitBtn.disabled = false;
		submitText.textContent = "Add Hostel";
		spinner.classList.add("hidden");
		return;
	}

	// If current form is for building -> call backend API
	if (currentLevel === "buildings") {
		try {
			const response = await fetch("http://localhost:8080/buildingWithHostelId", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({
					hostelId: parseInt(formData.get("hostelId")),
					name: formData.get("name"),
					floors: parseInt(formData.get("floors")) || 0,
					warden: formData.get("warden")
				})
			});

			if (response.ok) {
				showToast("Building added successfully");
				hideAddForm();
				await loadBuildings();  // Refresh the buildings list
			} else {
				showToast("Failed to add building", "error");
			}
		} catch (err) {
			console.error(err);
			showToast("Error connecting to backend", "error");
		}

		submitBtn.disabled = false;
		submitText.textContent = "Add Building";
		spinner.classList.add("hidden");
		return;
	}

	// For others, use dataSdk if available
	if (window.dataSdk) {
		const result = await window.dataSdk.create(itemData);
		submitBtn.disabled = false;
		submitText.textContent = `Add ${currentLevel.slice(0, -1)}`;
		spinner.classList.add('hidden');

		if (result.isOk) {
			showToast(`${currentLevel.slice(0, -1)} added successfully`);
			hideAddForm();

			if (currentLevel === 'rooms' && itemData.sharing > 0) {
				await createBedsForRoom(itemData.id, itemData.sharing);
			}
		} else {
			showToast(`Failed to add ${currentLevel.slice(0, -1)}`, 'error');
		}
	} else {
		// fallback local add
		allData.push(itemData);
		updateStats();
		renderCurrentLevel();

		submitBtn.disabled = false;
		submitText.textContent = `Add ${currentLevel.slice(0, -1)}`;
		spinner.classList.add('hidden');

		showToast('(local) item added');
		hideAddForm();

		if (currentLevel === 'rooms' && itemData.sharing > 0) {
			await createBedsForRoom(itemData.id, itemData.sharing);
		}
	}
}

async function createBedsForRoom(roomId, bedCount) {
	for (let i = 1; i <= bedCount; i++) {
		const bedData = {
			id: generateId(),
			entityType: 'bed',
			parentId: roomId,
			name: `Bed ${i}`,
			bedNo: i,
			status: 'available',
			price: 5000,
			createdAt: new Date().toISOString()
		};

		if (window.dataSdk) {
			await window.dataSdk.create(bedData);
		} else {
			allData.push(bedData);
		}
	}
	updateStats();
	renderCurrentLevel();
	showToast(`${bedCount} beds created automatically for the room`);
}

/* DOM events + initialization */
document.addEventListener('DOMContentLoaded', function() {
	const addBtn = document.getElementById('add-item-btn');
	const backBtn = document.getElementById('back-btn');

	addBtn.addEventListener('click', showAddForm);
	backBtn.addEventListener('click', navigateBack);

	// Form submission handler (delegated)
	document.addEventListener('submit', function(e) {
		if (e.target.id === 'item-form') {
			handleFormSubmit(e);
		}
	});

	// Initialize SDKs and load hostels
	initSdks().then(() => {
		// if dataSdk is present, it will call onDataChanged when data changes
		// But still attempt to load hostels from backend too (original behavior)
		loadHostels();
	});
});

/* Fetch hostels from backend (original had this) */
async function loadHostels() {
	try {
		const response = await fetch("http://localhost:8080/hostels");
		if (!response.ok) throw new Error("Failed to fetch hostels");

		const hostels = await response.json();

		// map into allData format for hostels (overwrites hostel entries)
		// We keep other entity types if present
		// Remove existing hostels
		allData = allData.filter(x => x.entityType !== 'hostel');

		const mapped = hostels.map(h => ({
			id: h.id,
			name: h.name,
			orgId: h.orgId,
			address: h.address,
			capacity: h.capacity,
			type: h.type,
			image: h.image || "",
			status: h.status || "active",
			entityType: "hostel",
			createdAt: h.createdAt || new Date().toISOString()
		}));

		allData = [...mapped, ...allData];

		currentLevel = "hostels";
		updateStats();
		renderCurrentLevel();
		showToast("Hostels loaded successfully");
	} catch (error) {
		console.error(error);
		showToast("Failed to load hostels", "error");
	}
}



/* Fetch buildings from backend (original had this) */
async function loadBuildings() {
	console.log("loadBuildings called");

	try {
		const response = await fetch("http://localhost:8080/buildings");
		console.log("Backend response status:", response.status);

		if (!response.ok) throw new Error("Failed to fetch buildings");

		const buildings = await response.json();
		console.log("Buildings from API:", buildings);

		// remove previous buildings data
		allData = allData.filter(x => x.entityType !== "building");

		const mapped = buildings.map(b => ({
			id: b.id,
			hostelId: b.hostelId,
			name: b.name,
			floors: b.floors,
			warden: b.warden,
			entityType: "building",
			createdAt: b.createdAt || new Date().toISOString()
		}));

		allData = [...mapped, ...allData];

		currentLevel = "buildings";
		updateStats();
		renderCurrentLevel();
		showToast("Buildings loaded successfully");

	} catch (error) {
		console.error("Error in loadBuildings():", error);
		showToast("Failed to load buildings", "error");
	}
}


