@Composable
fun HomeScreen() {
    val grades = (1..20).map { "Grade $it" }
    var selectedGradeIndex by remember { mutableIntStateOf(15) } // Default Grade 16 is index 15
    var selectedStepIndex by remember { mutableIntStateOf(15) } // Default Step 16
    val grade = selectedGradeIndex + 1
    val stepsList = PayScale2015.steps[grade] ?: emptyList()
    
    val stepsDropdown = stepsList.mapIndexed { index, value -> 
        "Step ${index + 1} — ${NumberFormatter.format(value)}"
    }

    // Reset step index if grade changes and previous step index is out of bounds
    LaunchedEffect(grade) {
        if (selectedStepIndex >= stepsList.size) {
            selectedStepIndex = 0
        }
    }
    
    val result = SalaryCalculator.calculate(grade, selectedStepIndex)
    val scrollState = rememberScrollState()
    
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DeveloperSidebar()
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFF120F16) // Dark premium background
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ParallaxBackground(scrollOffset = scrollState.value)
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppHeader(
                        scrollOffset = scrollState.value,
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
