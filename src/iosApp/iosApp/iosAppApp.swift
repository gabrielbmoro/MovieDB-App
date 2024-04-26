//
//  iosAppApp.swift
//  iosApp
//
//  Created by Gabriel Bronzatti Moro on 25/04/24.
//

import SwiftUI
import ComposeApp

@main
struct iosAppApp: App {

    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
